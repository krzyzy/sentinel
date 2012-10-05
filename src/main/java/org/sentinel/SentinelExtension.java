package org.sentinel;

import org.jboss.weld.introspector.WeldMethod;
import org.jboss.weld.introspector.WeldParameter;
import org.jboss.weld.util.reflection.HierarchyDiscovery;
import org.jboss.weld.util.reflection.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sentinel.scope.BeanDefinition;
import org.sentinel.scope.BeanDefinitionSlicer;
import org.sentinel.scope.BeforeInvocationContext;
import org.sentinel.scope.BeforeInvocationContextManager;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.*;
import javax.interceptor.InvocationContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Date: 02.01.12 22:14
 *
 * @author: Tomasz Krzyżak
 */
public class SentinelExtension implements Extension {

    private BeanDefinitionSlicer slicer = new BeanDefinitionSlicer();

    private static final Logger log = LoggerFactory.getLogger(SentinelExtension.class);


    public void afterBeanDiscovery(@Observes AfterBeanDiscovery event, BeanManager manager) {
        event.addContext(BeforeInvocationContext.getInstance());
        for (BeanDefinition bd : slicer.getSlices()) {
            MethodParameterBean bean = new MethodParameterBean(bd);
            log.debug("adding bean: " + bean.getName());
            event.addBean(bean);
        }
    }

    public void observeAnnotated(@Observes ProcessAnnotatedType type, BeanManager beanManager) {

        Sentinel sentinel = new Sentinel(beanManager);
        log.info("Analyzing annotated type: " + type.getAnnotatedType().toString());

        for (Object m : type.getAnnotatedType().getMethods()) {
            if (m instanceof WeldMethod) {
                for (Annotation a : ((WeldMethod) m).getAnnotations()) {
                    if (sentinel.isGuard(a.annotationType())) {
                        createBean((WeldMethod) m, beanManager);
                    }
                }
            }
        }
    }

    private void createBean(final WeldMethod m, final BeanManager beanManager) {
        Iterator iterator = m.getWeldParameters().iterator();
        while (iterator.hasNext()) {
            final WeldParameter wp = (WeldParameter) iterator.next();
            final Set<Annotation> annotations = getQualifiers(wp.getAnnotations(), beanManager);
            if (annotations.size() > 0) {
                log.info("Creating bean based on parameter \"" + wp.getPosition() + "\" of method: " + createMethodSingatureString(m));
                slicer.addBean(wp.getJavaClass(), annotations, wp.getTypeClosure());
            }
        }
    }

    private Set<Annotation> getQualifiers(Set<Annotation> annotations, BeanManager beanManager) {
        HashSet<Annotation> set = new HashSet<Annotation>();
        for (Annotation a : annotations) {
            if (beanManager.isQualifier(a.annotationType()))
                set.add(a);
        }
        return set;
    }

    private String createMethodSingatureString(WeldMethod m) {
        return m.getDeclaringType().getName() + "." + m.getName() + "()";
    }

    private class MethodParameterBean implements Bean {

        private Class<?> beanType;
        private Set<Annotation> qualifiers;
        private Set<Type> typeClosures;

        public MethodParameterBean(Class<?> beanType, Set<Annotation> qualifiers, Set<Type> typeClosures) {
            this.beanType = beanType;
            this.qualifiers = qualifiers;
            this.typeClosures = typeClosures;
        }

        public MethodParameterBean(BeanDefinition bd) {
            this(bd.getBeanClass(), bd.getQualifiers().all(), bd.getTypeClosures());
        }

        public Set<Type> getTypes() {
            return typeClosures;
        }

        public Set<Annotation> getQualifiers() {
            return qualifiers;
        }

        public Class<? extends Annotation> getScope() {
            return Dependent.class;
        }

        public String getName() {
            return beanType.getCanonicalName() + "[" + toString(typeClosures) + "]";
        }

        private String toString(Set<Type> typeClosures) {
            StringBuilder sb = new StringBuilder();
            for (Type t : typeClosures) {
                if (sb.length() > 0)
                    sb.append(", ");
                sb.append(t.toString());
            }
            return sb.toString();
        }

        public Set<Class<? extends Annotation>> getStereotypes() {
            return Collections.emptySet();
        }

        public Class<?> getBeanClass() {
            return beanType;
        }

        public boolean isAlternative() {
            return false;
        }

        public boolean isNullable() {
            return true;
        }

        public Set<InjectionPoint> getInjectionPoints() {
            return Collections.emptySet();
        }

        public Object create(CreationalContext creationalContext) {
            Type[] genericParameterTypes = BeforeInvocationContextManager.getInvocationContext().getMethod().getGenericParameterTypes();

            Object result = checkExactMatch(BeforeInvocationContextManager.getInvocationContext());
            if (result == null) {
                result = superTypesMatch(genericParameterTypes);
            }
            return result;
        }

        private Object superTypesMatch(Type[] genericParameterTypes) {
            Object match = null;
            for (int i = 0; i < genericParameterTypes.length; i++) {
                Type t = genericParameterTypes[i];
                if (Reflections.matches(typeClosures, new HierarchyDiscovery(t).getTypeClosure())) {
                    if (match != null) {
                        throw new IllegalStateException("Ambiguous injection values for " + getName());
                    }
                    match = BeforeInvocationContextManager.getInvocationContext().getParameters()[i];
                }
            }
            return match;
        }

        private Object checkExactMatch(InvocationContext invocationContext) {
            Type[] genericParameterTypes = invocationContext.getMethod().getGenericParameterTypes();
            for (int i = 0; i < genericParameterTypes.length; i++) {
                if (genericParameterTypes[i].equals(beanType)) {
                    return invocationContext.getParameters()[i];
                }
            }
            return null;
        }


        public void destroy(Object instance, CreationalContext creationalContext) {
        }
    }
}

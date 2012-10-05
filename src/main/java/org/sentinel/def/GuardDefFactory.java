package org.sentinel.def;

import org.sentinel.*;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Date: 11.12.11 20:46
 *
 * @author: Tomasz Krzyżak
 */

public class GuardDefFactory {

    private Sentinel sentinel;

    @Inject
    public GuardDefFactory(Sentinel sentinel) {
        this.sentinel = sentinel;
    }

    public GuardDef create(Annotation guardAnnotation) {
        GuardDef guardDef = new GuardDef(mockWithOverrides(guardAnnotation, Guard.class), guardAnnotation, sentinel);
        guardDef.setGuards(createGuardDefs(guardAnnotation.annotationType().getAnnotations()));
        return guardDef;
    }

    private <T extends Annotation> T mockWithOverrides(Annotation guardAnnotation, Class<T> annotationClass) {
        return mockGuardWithOverrides(guardAnnotation.annotationType().getAnnotation(annotationClass), getAttributeOverrides(guardAnnotation, annotationClass));
    }

    private <T extends Annotation> T mockGuardWithOverrides(final T annotation, final Map<String, Object> attributeOverrides) {

        InvocationHandler handler = new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals("annotationType")) {
                    return annotation.annotationType();
                } else {
                    if (attributeOverrides.containsKey(method.getName())) {
                        return (Class<? extends GuardRule>) attributeOverrides.get(method.getName());
                    } else
                        return method.invoke(annotation, args);
                }
            }
        };
        return (T) Proxy.newProxyInstance(annotation.getClass().getClassLoader(), new Class[]{annotation.annotationType()}, handler);
    }

    private Map<String, Object> getAttributeOverrides(Annotation guardAnnotation, Class<?> overridenAnnoattion) {
        Map<String, Object> result = new HashMap<String, Object>();
        for (Method member : guardAnnotation.annotationType().getMethods()) {
            if (member.isAnnotationPresent(OverridesAttribute.class)) {
                OverridesAttribute overridesAttribute = member.getAnnotation(OverridesAttribute.class);
                if (overridenAnnoattion.equals(overridesAttribute.guard())) {
                    try {
                        overridenAnnoattion.getMethod(overridesAttribute.name());
                    } catch (NoSuchMethodException e) {
                        throw new InvalidOverrideException(member);
                    }
                    try {
                        result.put(overridesAttribute.name(), member.invoke(guardAnnotation));
                    } catch (Exception e) {
                        throw new RuntimeException("Could not extract attribute overrides", e);
                    }
                }
            }
        }
        return result;
    }

    public List<GuardDef<?>> createGuardDefs(Annotation[] annotations) {
        List<GuardDef<?>> guardDefList = new LinkedList<GuardDef<?>>();
        for (Annotation guardAnnotation : annotations) {
            if (sentinel.isGuard(guardAnnotation.annotationType())) {
                guardDefList.add(create(guardAnnotation));
            }
        }
        return guardDefList;
    }


    public Sentinel getSentinel() {
        return sentinel;
    }
}

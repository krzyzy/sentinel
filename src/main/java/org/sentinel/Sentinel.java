package org.sentinel;


import org.jboss.weld.bootstrap.api.helpers.SimpleServiceRegistry;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.metadata.TypeStore;
import org.jboss.weld.resources.ClassTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sentinel.def.GuardDef;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * Date: 23.12.11 23:26
 *
 * @author: Tomasz Krzyżak
 */
public class Sentinel {

    private static final Logger logger = LoggerFactory.getLogger(Sentinel.class);

    private BeanManager beanManager;

    public static Sentinel create() {
        SimpleServiceRegistry serviceRegistry = new SimpleServiceRegistry();
        serviceRegistry.add(ClassTransformer.class, new ClassTransformer(new TypeStore()));
        return new Sentinel(BeanManagerImpl.newRootManager("id", serviceRegistry));

    }

    @Inject
    public Sentinel(BeanManager beanManager) {
        this.beanManager = beanManager;
    }

    // TODO: think about placing this method somewhere else.
    public <T extends Annotation> SentinelContext<T> createContext(Guard guard, T guardAnnotation) {
        return new SentinelContext<T>(guardAnnotation, guard, this);
    }

    public <T extends Annotation> GuardRule<T> createGuardRule(GuardDef<T> same) {
        beanManager.getBeans(Integer.class);
        AnnotatedType<GuardRule<T>> type = (AnnotatedType<GuardRule<T>>) beanManager.createAnnotatedType(same.getRuleClass());
        InjectionTarget<GuardRule<T>> target = beanManager.createInjectionTarget(type);
        CreationalContext<GuardRule<T>> ctx = beanManager.createCreationalContext(null);

        GuardRule<T> ruleInstance = target.produce(ctx);
        target.postConstruct(ruleInstance);
        target.inject(ruleInstance, ctx);

        return ruleInstance;
    }

    public boolean isGuard(Class<? extends Annotation> aClass) {
        return aClass.isAnnotationPresent(Guard.class);
    }

    public static <T extends Annotation> boolean qualifierEquals(T t1, T t2) {
        for (Method m : t1.annotationType().getDeclaredMethods()) {
            Object thisValue = invoke(m, t1);
            Object thatValue = invoke(m, t2);
            if (thisValue instanceof byte[] && thatValue instanceof byte[]) {
                if (!Arrays.equals((byte[]) thisValue, (byte[]) thatValue)) return false;
            } else if (thisValue instanceof short[] && thatValue instanceof short[]) {
                if (!Arrays.equals((short[]) thisValue, (short[]) thatValue)) return false;
            } else if (thisValue instanceof int[] && thatValue instanceof int[]) {
                if (!Arrays.equals((int[]) thisValue, (int[]) thatValue)) return false;
            } else if (thisValue instanceof long[] && thatValue instanceof long[]) {
                if (!Arrays.equals((long[]) thisValue, (long[]) thatValue)) return false;
            } else if (thisValue instanceof float[] && thatValue instanceof float[]) {
                if (!Arrays.equals((float[]) thisValue, (float[]) thatValue)) return false;
            } else if (thisValue instanceof double[] && thatValue instanceof double[]) {
                if (!Arrays.equals((double[]) thisValue, (double[]) thatValue)) return false;
            } else if (thisValue instanceof char[] && thatValue instanceof char[]) {
                if (!Arrays.equals((char[]) thisValue, (char[]) thatValue)) return false;
            } else if (thisValue instanceof boolean[] && thatValue instanceof boolean[]) {
                if (!Arrays.equals((boolean[]) thisValue, (boolean[]) thatValue)) return false;
            } else if (thisValue instanceof Object[] && thatValue instanceof Object[]) {
                if (!Arrays.equals((Object[]) thisValue, (Object[]) thatValue)) return false;
            } else {
                if (!thisValue.equals(thatValue)) return false;
            }


        }

        return true;
    }

    public static int qualifierHashCode(Annotation i) {
        int hashCode = 0;
        for (Method member : i.annotationType().getDeclaredMethods()) {
            int memberNameHashCode = 127 * member.getName().hashCode();
            Object value = invoke(member, i);
            int memberValueHashCode;
            if (value instanceof boolean[]) {
                memberValueHashCode = Arrays.hashCode((boolean[]) value);
            } else if (value instanceof short[]) {
                memberValueHashCode = Arrays.hashCode((short[]) value);
            } else if (value instanceof int[]) {
                memberValueHashCode = Arrays.hashCode((int[]) value);
            } else if (value instanceof long[]) {
                memberValueHashCode = Arrays.hashCode((long[]) value);
            } else if (value instanceof float[]) {
                memberValueHashCode = Arrays.hashCode((float[]) value);
            } else if (value instanceof double[]) {
                memberValueHashCode = Arrays.hashCode((double[]) value);
            } else if (value instanceof byte[]) {
                memberValueHashCode = Arrays.hashCode((byte[]) value);
            } else if (value instanceof char[]) {
                memberValueHashCode = Arrays.hashCode((char[]) value);
            } else if (value instanceof Object[]) {
                memberValueHashCode = Arrays.hashCode((Object[]) value);
            } else {
                memberValueHashCode = value.hashCode();
            }
            hashCode += memberNameHashCode ^ memberValueHashCode;
        }
        return hashCode;
    }

    private static Object invoke(Method method, Object instance) {
        try {
            if (!method.isAccessible()) method.setAccessible(true);
            return method.invoke(instance);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error checking value of member method " + method.getName() + " on " + method.getDeclaringClass(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error checking value of member method " + method.getName() + " on " + method.getDeclaringClass(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Error checking value of member method " + method.getName() + " on " + method.getDeclaringClass(), e);
        }
    }

    public static boolean typeCloseEquals(Type type, Type type1) {
        return type1.toString().equals(type1.toString());
    }

    public static int typeCloseHashCode(Type t) {
        return t.toString().hashCode();
    }
}

package org.sentinel.scope;

import org.jboss.weld.util.reflection.Reflections;
import org.sentinel.Sentinel;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: jan
 * Date: 31.01.12
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
public class BeanDefinition {

    private BeanDefinitionQualifiers qualifiers;
    private Class<?> beanClass;
    private List<Type> typeClosures = new LinkedList<Type>();
    private Class<Object> beanType;

    public BeanDefinition(BeanDefinitionQualifiers qulifiers, Class<?> beanClass, Set<Type> typeClosures) {
        init(qulifiers, beanClass, typeClosures);
    }

    private void init(BeanDefinitionQualifiers qulifiers, Class<?> beanClass, Set<Type> typeClosures) {
        this.qualifiers = qulifiers;
        this.beanClass = beanClass;
        this.typeClosures.addAll(typeClosures);
        Collections.sort(this.typeClosures, new Comparator<Type>() {
            public int compare(Type o1, Type o2) {
                return o1.toString().compareTo(o2.toString());
            }
        });
    }

    public BeanDefinition(BeanDefinitionQualifiers qualifiers, Type t) {
        if (t instanceof ParameterizedType) {
            init(qualifiers, (Class<?>) ((ParameterizedType) t).getRawType(), new HashSet<Type>(Arrays.asList(t)));
        } else if (t instanceof Class) {
            init(qualifiers, (Class<?>) t, new HashSet<Type>(Arrays.asList(t)));
        } else if (t instanceof GenericArrayType) {
            Class<? extends Object> aClass = Array.newInstance((Class<?>) ((GenericArrayType) t).getGenericComponentType(), 1).getClass();
            init(qualifiers, aClass, new HashSet<Type>(Arrays.asList(t)));
        } else {
            throw new IllegalArgumentException("unknown type: "+t);
        }
    }

    public BeanDefinitionQualifiers getQualifiers() {
        return qualifiers;
    }

    public Set<Type> getTypeClosures() {
        return new HashSet<Type>(typeClosures);
    }

    public BeanDefinition trimTypeClosures(Set<Type> toTrimm) {
        HashSet<Type> remainingTypeClosures = new HashSet<Type>(typeClosures);
        for (Type t1 : typeClosures) {
            for (Type t2 : toTrimm) {
                if (Reflections.matches(t1, t2)) {
                    remainingTypeClosures.remove(t1);
                }
            }
        }
        return new BeanDefinition(qualifiers, beanClass, remainingTypeClosures);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BeanDefinition that = (BeanDefinition) o;

        if (qualifiers != null ? !qualifiers.equals(that.qualifiers) : that.qualifiers != null) return false;
        if (this.typeClosures.size()!=that.typeClosures.size())
            return false;
        for (int i=0; i<this.typeClosures.size(); i++) {
            if (!Sentinel.typeCloseEquals(this.typeClosures.get(i), that.typeClosures.get(i))) {
                 return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = qualifiers != null ? qualifiers.hashCode() : 0;        
        result = 31 * result;
        for (Type t : typeClosures ){
            result+=Sentinel.typeCloseHashCode(t);
        }
        return result;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }
}

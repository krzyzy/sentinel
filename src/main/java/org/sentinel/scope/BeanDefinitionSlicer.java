package org.sentinel.scope;

import org.jboss.weld.util.reflection.Reflections;

import javax.enterprise.inject.Default;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: jan
 * Date: 30.01.12
 * Time: 22:21
 * To change this template use File | Settings | File Templates.
 */
public class BeanDefinitionSlicer {


    private Map<BeanDefinitionQualifiers, Set<BeanDefinition>> slices = new HashMap<BeanDefinitionQualifiers, Set<BeanDefinition>>();

    public void addBean(Class<?> beanClass, Set<Annotation> qulifiers, Set<Type> typeClosures) {
        qulifiers = ensureDefault(qulifiers);
        BeanDefinitionQualifiers qulifiers1 = new BeanDefinitionQualifiers(qulifiers);
        BeanDefinition beanDefinition = new BeanDefinition(qulifiers1, beanClass, typeClosures);
        if (!slices.containsKey(beanDefinition.getQualifiers())) {
            addNewSlice(beanDefinition);
        } else if (!containsBeanOfClass(beanDefinition)) {
            mergeSlices(beanDefinition);
        }
    }

    private boolean containsBeanOfClass(BeanDefinition beanClass) {
        for (BeanDefinition bd : slices.get(beanClass.getQualifiers())) {
            if (bd.getBeanClass().equals(beanClass.getBeanClass()))
                return true;
        }
        return false;
    }

    private Set<Annotation> ensureDefault(Set<Annotation> qulifiers) {
        HashSet<Annotation> annotations = new HashSet<Annotation>(qulifiers);
        annotations.add(new Default() {
            public Class<? extends Annotation> annotationType() {
                return Default.class;
            }
        });
        return annotations;
    }

    private void mergeSlices(BeanDefinition beanDefinition) {
        Set<BeanDefinition> removed = removeMatching(beanDefinition);
        Set<BeanDefinition> sliced = slice(removed, beanDefinition);
        slices.get(beanDefinition.getQualifiers()).addAll(sliced);
    }

    private Set<BeanDefinition> slice(Set<BeanDefinition> existing, BeanDefinition _new) {
        Set<Type> typeClosures = _new.getTypeClosures();
        Set<BeanDefinition> result = new HashSet<BeanDefinition>();
        for (BeanDefinition existingDefinition : existing) {
            Set<BeanDefinition> sliced = slice(existingDefinition, typeClosures);
            result.addAll(sliced);
        }
        for (BeanDefinition bd : result) {
            remove(typeClosures, bd.getTypeClosures());
        }
        if (typeClosures.size() > 0) {
            result.add(new BeanDefinition(_new.getQualifiers(), _new.getBeanClass(), typeClosures));
        }
        return result;
    }

    private void remove(Set<Type> from, Set<Type> toRemove) {
        Iterator<Type> iterator = from.iterator();
        while (iterator.hasNext()) {
            Type t1 = iterator.next();
            for (Type t2 : toRemove) {
                if (Reflections.matches(t1, t2))
                    iterator.remove();
            }
        }
    }

    private Set<BeanDefinition> slice(BeanDefinition existingDefinition, Set<Type> newDefinition) {
        Set<BeanDefinition> result = new HashSet<BeanDefinition>();
        Set<Type> typeClosuresIntersection = intersection(existingDefinition.getTypeClosures(), newDefinition);
        existingDefinition = existingDefinition.trimTypeClosures(typeClosuresIntersection);


        result.addAll(createBeanDefinitions(existingDefinition.getQualifiers(), typeClosuresIntersection));
        if (existingDefinition.getTypeClosures().size() > 0)
            result.add(existingDefinition);


        return result;
    }

    private Set<BeanDefinition> createBeanDefinitions(BeanDefinitionQualifiers qualifiers, Set<Type> typeClosuresIntersection) {
        Set<BeanDefinition> beanDefinitions = new HashSet<BeanDefinition>();
        for (Type t : typeClosuresIntersection) {
            beanDefinitions.add(new BeanDefinition(qualifiers, t));
        }
        return beanDefinitions;
    }

    private Set<Type> intersection(Set<Type> typeClosures, Set<Type> typeClosures1) {
        Set<Type> result = new HashSet<Type>();
        for (Type t1 : typeClosures1) {
            for (Type t2 : typeClosures) {
                if (Reflections.matches(t1, t2)) {
                    result.add(t1);
                }
            }
        }
        return result;
    }


    private Set<BeanDefinition> removeMatching(BeanDefinition beanDefinition) {
        Set<BeanDefinition> result = new HashSet<BeanDefinition>();

        Iterator<BeanDefinition> iterator = slices.get(beanDefinition.getQualifiers()).iterator();
        while (iterator.hasNext()) {
            BeanDefinition next = iterator.next();
            if (Reflections.matches(next.getTypeClosures(), beanDefinition.getTypeClosures())) {
                result.add(next);
                iterator.remove();
            }
        }
        return result;
    }

    private void addNewSlice(BeanDefinition beanDefinition) {
        Set<BeanDefinition> beanDefinitions = new HashSet<BeanDefinition>();
        slices.put(beanDefinition.getQualifiers(), beanDefinitions);
        beanDefinitions.add(beanDefinition);
    }

    public Collection<BeanDefinition> getSlices() {
        Collection<BeanDefinition> result = new LinkedList<BeanDefinition>();
        for (Set<BeanDefinition> definitions : this.slices.values()) {
            result.addAll(definitions);
        }
        return result;
    }
}

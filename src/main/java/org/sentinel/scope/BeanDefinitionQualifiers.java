package org.sentinel.scope;

import org.sentinel.Sentinel;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: jan
 * Date: 31.01.12
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
public class BeanDefinitionQualifiers {
    private List<Annotation> qualifiers = new LinkedList<Annotation>();

    public BeanDefinitionQualifiers(Set<Annotation> qualifiers) {
        this.qualifiers.addAll(qualifiers);
        Collections.sort(this.qualifiers, new Comparator<Annotation>() {
            public int compare(Annotation o1, Annotation o2) {
                return o1.annotationType().getCanonicalName().compareTo(o2.annotationType().getCanonicalName());
            }
        });
    }

    public Set<Annotation> all() {
        return new HashSet<Annotation>(qualifiers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BeanDefinitionQualifiers that = (BeanDefinitionQualifiers) o;

        if (that.qualifiers.size() != this.qualifiers.size())
            return false;
        for (int i = 0; i < qualifiers.size(); i++) {
            if (!Sentinel.qualifierEquals(this.qualifiers.get(i), that.qualifiers.get(i)))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        for (Annotation a : qualifiers) {
            hashCode += Sentinel.qualifierHashCode(a);
        }
        return hashCode;
    }
}

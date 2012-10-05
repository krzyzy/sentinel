package org.sentinel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * Date: 06.12.11 18:54
 *
 * @author: Tomasz Krzyżak
 */
public class AttributeOverridingValidator {

    /**
     * inspects given method annotation's member for overriding rules violations.
     *
     * @param method
     *         method member of annotation
     * @return list of attribute overriding rules violations
     */
    public List<AttributeOverridingViolation> validate(Method method) {
        if (!Annotation.class.isAssignableFrom(method.getDeclaringClass()))
            throw new IllegalArgumentException("BeforeInvocationScope " + method.toString() + " is not annotation member.");
        return Collections.emptyList();
    }
}

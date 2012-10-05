package org.sentinel;

import java.lang.reflect.Method;

/**
 * Date: 06.12.11 18:38
 *
 * @author: Tomasz Krzyżak
 */
public class InvalidOverrideException extends RuntimeException {

    public InvalidOverrideException(Method member) {
        super(composeMessage(member));


    }

    private static String composeMessage(Method member) {
        OverridesAttribute annotation = member.getAnnotation(OverridesAttribute.class);

        StringBuilder sb = new StringBuilder();
        sb.append("Invalid override definition at \"").append(member.toString()).append("\". Annotation ").append(annotation.guard().getName()).append(" does not define member with name: ").append(annotation.name());
        return sb.toString();
    }
}

package org.sentinel.guards;

import org.sentinel.Guard;
import org.sentinel.OverridesAttribute;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Date: 06.12.11 18:34
 *
 * @author: Tomasz Krzyżak
 */
@Guard
@Retention(RetentionPolicy.RUNTIME)
public @interface SampleGuardWithInvalidOverrides {

    @OverridesAttribute(guard = Guard.class, name = "wrongvalue") String value();
}

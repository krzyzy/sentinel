package org.sentinel.guards;

import org.sentinel.Guard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Date: 07.12.11 17:27
 *
 * @author: Tomasz Krzyżak
 */
@Guard(ComposedGuardRule.class)
@Retention(RetentionPolicy.RUNTIME)
@RolesAllowed({"role1"})
public @interface ComposedGuard {
}

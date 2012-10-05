package org.sentinel.guards.operation;

import org.sentinel.Operation;
import org.sentinel.OverridesAttribute;
import org.sentinel.operations.DefaultPermissionResolver;
import org.sentinel.operations.PermissionResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Date: 26.11.11 14:38
 *
 * @author: Tomasz Krzyżak
 */
@Operation("update")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Update {

    @OverridesAttribute(guard = Operation.class, name = "resourceType") Class<?> value();

    @OverridesAttribute(guard = Operation.class, name = "permissionResolver") Class<? extends PermissionResolver> resolver() default DefaultPermissionResolver.class;
}

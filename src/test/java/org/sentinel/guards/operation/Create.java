package org.sentinel.guards.operation;

import org.sentinel.Operation;
import org.sentinel.OverridesAttribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Date: 25.11.11 23:26
 *
 * @author: Tomasz Krzyżak
 */
@Operation(value = "create")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Create {

    @OverridesAttribute(guard = Operation.class, name = "resourceType") Class<?> value();

}

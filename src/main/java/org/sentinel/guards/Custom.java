package org.sentinel.guards;

import org.sentinel.Guard;
import org.sentinel.GuardRule;
import org.sentinel.OverridesAttribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom guard annotation used at method to indicate that some rule ("resourceType") guards method invocations
 *
 * @author Tomasz Krzyżak
 *         Date: 24.11.11 18:44
 */
@Guard(GuardRule.VoidGuardRule.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Custom {

    /**
     * Type of rule that will be executed when method annotated by @Custom is invoked.
     *
     * @return
     */
    @OverridesAttribute(guard = Guard.class) Class<? extends GuardRule<Custom>> value();

}

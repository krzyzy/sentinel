package org.sentinel.guards;

import org.sentinel.Guard;
import org.sentinel.GuardRule;
import org.sentinel.OverridesAttribute;

import java.lang.annotation.*;

/**
 * Date: 06.12.11 18:08
 *
 * @author: Tomasz Krzyżak
 */
@Guard
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface SampleGuardRuleWithOverrides {

    @OverridesAttribute(guard = Guard.class, name = "value") Class<? extends GuardRule<SampleGuardRuleWithOverrides>> value();

}

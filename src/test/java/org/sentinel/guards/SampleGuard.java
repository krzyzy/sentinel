package org.sentinel.guards;

import org.sentinel.Guard;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Date: 01.12.11 23:31
 *
 * @author: Tomasz Krzyżak
 */
@Guard(SampleGuardRule.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SampleGuard {



}

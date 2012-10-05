package org.sentinel.guards.operation;

import org.sentinel.Operation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Date: 26.11.11 14:21
 *
 * @author: Tomasz Krzyżak
 */
@Operation("sampleOperation")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SampleOperation {
}

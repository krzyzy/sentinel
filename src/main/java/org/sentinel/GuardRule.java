package org.sentinel;

import java.lang.annotation.Annotation;

/**
 * Defines the logic to check if scope is permitted.
 * Implementators must comply to following rules:
 * <ul>
 * <li>parameter T must resolve to annotation annotated by {pl.accuratus.sentinel.@Guard}</li>
 * <li>annotation {@pl.accuratus.sentinel.Guard} placed at T must declare value for "value" member that resolves to non
 * parameterized, non abstract type or T overrides (see {@pl.accuratus.sentinel.OverridesAttribute}) member "value" of
 * </li>
 * </ul>
 *
 * @author Tomasz Krzyżak
 * @see OverridesAttribute
 */
public interface GuardRule<T extends Annotation> {
    
    public static class VoidGuardRule implements GuardRule<Annotation> {

        public boolean isExecutePermitted(SentinelContext<Annotation> context) {
            return true;
        }
    }

    boolean isExecutePermitted(SentinelContext<T> context);

}

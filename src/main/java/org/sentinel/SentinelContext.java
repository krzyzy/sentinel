package org.sentinel;

import java.lang.annotation.Annotation;

/**
 * Still no idea what responsibility this class should have.
 *
 * @author Tomasz Krzyżak
 *         <p/>
 *         Date: 24.11.11 20:56
 */
public class SentinelContext<T extends Annotation> {

    private T guardAnnotation;
    private Sentinel sentinel;
    private Guard guard;

    SentinelContext(T guardAnnotation, Guard guard, Sentinel sentinel) {
        this.guardAnnotation = guardAnnotation;
        this.guard = guard;
        this.sentinel = sentinel;
    }

    public T getGuardAnnotation() {
        return guardAnnotation;
    }

    public Sentinel getSentinel() {
        return sentinel;
    }

    public Guard getGuard() {
        return guard;
    }
}

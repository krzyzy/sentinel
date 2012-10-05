package org.sentinel;

import org.sentinel.Guard;
import org.sentinel.GuardRule;
import org.sentinel.SentinelContext;

import java.lang.annotation.Annotation;

/**
 * Date: 24.12.11 23:12
 *
 * @author: Tomasz Krzyżak
 */
public abstract class BaseSentinelTest {

    public static @interface UnderTestAnnotation {
    }

    public static class UnderTestGuardRule implements GuardRule<UnderTestAnnotation> {
        public boolean isExecutePermitted(SentinelContext<UnderTestAnnotation> underTestAnnotationSentinelContext) {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }

    }

    UnderTestAnnotation guardAnnotation = new UnderTestAnnotation() {
        public Class<? extends Annotation> annotationType() {
            return UnderTestAnnotation.class;
        }
    };

    Guard guard = new Guard() {
        public Class<? extends GuardRule<?>> value() {
            return UnderTestGuardRule.class;
        }

        public Class<? extends Annotation> annotationType() {
            return Guard.class;
        }
    };
}

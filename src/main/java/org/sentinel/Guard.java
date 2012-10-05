package org.sentinel;

import java.lang.annotation.*;

/**
 * marker annotation used to identify guard annotations. A typical guard annotation is:
 * <ul>
 * <li>is annotated with {@Guard}, {@Retention(RetentionPolicy.RUNTIME)}</li>
 * <li>can have attributes</li>
 * <li>can define members and annotate them with {@pl.accuratus.sentinel.OverridesAttribute} to override composing
 * operations or
 * {@pl.accuratus.sentinel.Guard} members</li>
 * </ul>
 * </ul>
 *
 * @author Tomasz Krzyzak
 *         <p/>
 *         Date: 24.11.11 18:19
 * @see org.sentinel.guards.Custom
 * @see org.sentinel.guards.RolesAllowed
 * @see Operation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Documented
public @interface Guard {

    Class<? extends GuardRule<?>> value() default GuardRule.VoidGuardRule.class;

}

package org.sentinel;

import java.lang.annotation.*;

/**
 * Mark an attribute as overriding the attribute of a composing guard or {@pl.accuratus.sentinel.Guard}.
 * <p/>
 * <ul>
 * <li>both attributes must share the same type</li>
 * <li>annotated attribute's owner must be annotated by annotation specified at <b>"guard\"</b> attribute</li>
 * </ul>
 * <p/>
 *
 * @author Tomasz Krzyżak
 *         <p/>
 *         Date: 24.11.11 22:20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface OverridesAttribute {

    /** @return Guard type the attribute is overriding */
    Class<? extends Annotation> guard();

    /**
     * Name of the Guard attribute overridden.
     * Defaults to the value of the attribute hosting <code>@OverridesAttribute</code>.
     *
     * @return value of Guard attribute overridden.
     */
    String name() default "";

}

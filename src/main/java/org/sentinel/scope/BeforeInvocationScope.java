package org.sentinel.scope;

import javax.enterprise.context.NormalScope;
import java.lang.annotation.*;

/**
 * Date: 26.12.11 09:20
 *
 * @author: Tomasz Krzyżak
 */
@NormalScope
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BeforeInvocationScope {


}

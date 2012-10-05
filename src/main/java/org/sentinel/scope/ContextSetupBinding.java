package org.sentinel.scope;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

/**
 * Date: 02.01.12 21:52
 *
 * @author: Tomasz Krzyżak
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@InterceptorBinding
@Inherited
public @interface ContextSetupBinding {
}

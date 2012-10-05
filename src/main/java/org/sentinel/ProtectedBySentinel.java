package org.sentinel;

import org.sentinel.scope.ContextSetupBinding;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

/**
 * Date: 26.12.11 00:43
 *
 * @author: Tomasz Krzyżak
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ContextSetupBinding
@InterceptorBinding
@Inherited
public @interface ProtectedBySentinel {

}

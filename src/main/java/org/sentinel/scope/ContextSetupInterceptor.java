package org.sentinel.scope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * Date: 02.01.12 21:47
 *
 * @author: Tomasz Krzyżak
 */
@Interceptor
@ContextSetupBinding
public class ContextSetupInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ContextSetupInterceptor.class);

       @AroundInvoke
    public Object mdbInterceptor(InvocationContext ctx) throws Exception {
        BeforeInvocationContext.getInstance().activate();
        BeforeInvocationContextManager.setInvocationContext(ctx);

        return ctx.proceed();
    }
}

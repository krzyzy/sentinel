package org.sentinel.scope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sentinel.ProtectedBySentinel;
import org.sentinel.Sentinel;
import org.sentinel.def.RulesInvoker;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * This interceptor provides Sentinel functionality.
 *
 * @author: Tomasz Krzyżak
 * <p/>
 * Date: 29.11.11 23:09
 */
@Interceptor
@ProtectedBySentinel
@ContextSetupBinding
public class SentinelInterceptor {

    private static final Logger log = LoggerFactory.getLogger(SentinelInterceptor.class);

    public Sentinel getSentinel() {
        return sentinel;
    }

    @Inject
    Sentinel sentinel;

    @Inject
    BeanManager beanManager;

    @Inject
    @BeforeInvocationScope
    InvocationContext ctx;

    @Inject
    @BeforeInvocationScope
    RulesInvoker methodSecurityDef;

    @AroundInvoke
    public Object mdbInterceptor(InvocationContext ctx) throws Exception {
        log.info("Sentinel protecting method: \"" + ctx.getMethod().toGenericString() + "\" " + (methodSecurityDef.isExecutePermitted() ? "allows execution" : "rejects execution"));

        BeforeInvocationContext.getInstance().deActivate();

        return ctx.proceed();
    }

}

package org.sentinel.scope;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.interceptor.InvocationContext;

/**
 * Date: 26.12.11 09:16
 *
 * @author: Tomasz Krzyżak
 */
@ApplicationScoped
public class BeforeInvocationContextManager {

    private static InvocationContext invocationContext;

    static void setInvocationContext(InvocationContext invocationContext) {
        BeforeInvocationContextManager.invocationContext = invocationContext;
    }

    public static InvocationContext getInvocationContext(){
        return invocationContext;
    }

    @Produces
    @BeforeInvocationScope
    public InvocationContext getInvocationContextProducerMethod() {
        return invocationContext;
    }

}

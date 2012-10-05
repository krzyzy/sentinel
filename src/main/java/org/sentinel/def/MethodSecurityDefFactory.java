package org.sentinel.def;

import org.sentinel.scope.BeforeInvocationScope;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Date: 01.12.11 23:46
 *
 * @author: Tomasz Krzyżak
 */
public class MethodSecurityDefFactory implements Serializable {


    private GuardDefFactory guardDefFactory;

    @Inject
    public MethodSecurityDefFactory(GuardDefFactory guardDefFactory) {
        this.guardDefFactory = guardDefFactory;
    }

    public MethodSecurityDef create(Method method) {
        return new MethodSecurityDef(guardDefFactory.createGuardDefs(method.getAnnotations()));
    }

    public MethodSecurityDef create(InvocationContext ic) {
        return new MethodSecurityDef(guardDefFactory.createGuardDefs(ic.getMethod().getAnnotations()));
    }


    public static MethodSecurityDef create(GuardDefFactory guardDefFactory, InvocationContext method) {
        return new MethodSecurityDef(guardDefFactory.createGuardDefs(method.getMethod().getAnnotations()));
    }

    @Produces
    @BeforeInvocationScope
    public RulesInvoker produce(InvocationContext invocationContext) {
        return create(guardDefFactory, invocationContext);
    }


}

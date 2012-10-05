package org.sentinel.guards;

import org.sentinel.Guard;
import org.sentinel.GuardRule;
import org.sentinel.SentinelContext;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Simple rule using {@javax.ejb.SessionContext} to ensure that invoking user is in one or all (depending on selector
 * member) roles
 *
 * @author Tomasz Krzyżak
 *         <p/>
 *         Date: 24.11.11 18:30
 */
@Guard(RolesAllowed.Rule.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface RolesAllowed {

    public static class Rule implements GuardRule<RolesAllowed> {

        @Resource
        private SessionContext sessionContext;

        public boolean isExecutePermitted(SentinelContext<RolesAllowed> context) {
            switch (context.getGuardAnnotation().selector()) {
                case ANY:
                    return new AnyRoleRule(sessionContext).isExecutePermitted(context);
                case ALL:
                    return new AllRolesRule(sessionContext).isExecutePermitted(context);
                default:
                    throw new IllegalStateException("unknown permissionResolver");
            }
        }

    }

    class AnyRoleRule implements GuardRule<RolesAllowed> {

        private SessionContext sessionContext;

        public AnyRoleRule(SessionContext sessionContext) {
            this.sessionContext = sessionContext;
        }

        public boolean isExecutePermitted(SentinelContext<RolesAllowed> context) {
            for (String roleName : context.getGuardAnnotation().value()) {
                if (sessionContext.isCallerInRole(roleName))
                    return true;
            }
            return false;
        }
    }

    class AllRolesRule implements GuardRule<RolesAllowed> {

        private SessionContext sessionContext;

        public AllRolesRule(SessionContext sessionContext) {
            this.sessionContext = sessionContext;
        }

        public boolean isExecutePermitted(SentinelContext<RolesAllowed> context) {
            for (String roleName : context.getGuardAnnotation().value()) {
                if (!sessionContext.isCallerInRole(roleName))
                    return false;
            }
            return true;
        }
    }

    public static enum RoleSelector {
        ANY, ALL
    }

    String[] value();

    RoleSelector selector() default RoleSelector.ANY;

}

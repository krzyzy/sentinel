package org.sentinel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.sentinel.Guard;
import org.sentinel.GuardRule;
import org.sentinel.Sentinel;
import org.sentinel.SentinelContext;
import org.sentinel.guards.RolesAllowed;

import javax.ejb.SessionContext;
import java.lang.annotation.Annotation;

/**
 * Date: 24.11.11 21:41
 *
 * @author Tomasz Krzyżak
 */
@RunWith(MockitoJUnitRunner.class)
public class RolesAllowedRuleTest {

    @Mock
    SessionContext context;

    @InjectMocks
    RolesAllowed.Rule rule = new RolesAllowed.Rule();
    private Sentinel sentinel = Sentinel.create();
    private Guard guard;

    @Before
    public void setUp() throws Exception {
        guard = new Guard() {
            public Class<? extends GuardRule<?>> value() {
                return null;
            }

            public Class<? extends Annotation> annotationType() {
                return Guard.class;
            }
        };
    }

    @Test
    public void testAnyRole() {
        final String role1 = "role1";
        final String role2 = "role2";

        RolesAllowed annotation = new RolesAllowed() {
            public String[] value() {
                return new String[]{role1, role2};
            }

            public RoleSelector selector() {
                return RoleSelector.ANY;
            }

            public Class<? extends Annotation> annotationType() {
                return RolesAllowed.class;
            }
        };
        SentinelContext<RolesAllowed> guardRuleContext = sentinel.createContext(guard, annotation);

        Assert.assertFalse(rule.isExecutePermitted(guardRuleContext));
        Mockito.doReturn(true).when(context).isCallerInRole(Matchers.eq(role1));

        Assert.assertTrue(rule.isExecutePermitted(guardRuleContext));
    }

    @Test
    public void testAllRoles() {
        final String role1 = "role1";
        final String role2 = "role2";

        RolesAllowed annotation = new RolesAllowed() {
            public String[] value() {
                return new String[]{role1, role2};
            }

            public RoleSelector selector() {
                return RoleSelector.ALL;
            }

            public Class<? extends Annotation> annotationType() {
                return RolesAllowed.class;
            }
        };
        SentinelContext<RolesAllowed> guardRuleContext = sentinel.createContext(guard, annotation);
        Assert.assertFalse(rule.isExecutePermitted(guardRuleContext));

        Mockito.doReturn(true).when(context).isCallerInRole(Matchers.eq(role1));
        Assert.assertFalse(rule.isExecutePermitted(guardRuleContext));

        Mockito.doReturn(true).when(context).isCallerInRole(Matchers.eq(role2));
        Assert.assertTrue(rule.isExecutePermitted(guardRuleContext));
    }

}

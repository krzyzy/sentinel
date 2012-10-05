package org.sentinel;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.sentinel.Guard;
import org.sentinel.Sentinel;
import org.sentinel.def.GuardDefFactory;
import org.sentinel.def.MethodSecurityDefFactory;
import org.sentinel.def.GuardDef;
import org.sentinel.def.MethodSecurityDef;
import org.sentinel.guards.AlternateSampleGuardRule;
import org.sentinel.guards.RolesAllowed;
import org.sentinel.guards.SampleGuard;
import org.sentinel.guards.SampleGuardRule;
import org.sentinel.guards.sample.SampleService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Date: 01.12.11 23:36
 *
 * @author: Tomasz Krzyżak
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultMethodSecurityDefFactoryTest {

    @Spy
    Sentinel sentinel = Sentinel.create();

    @Spy
    private GuardDefFactory guardDefFactory = new GuardDefFactory(sentinel);

    private MethodSecurityDefFactory methodSecurityDefFactory;

    @Before
    public void before() throws NoSuchMethodException {
        methodSecurityDefFactory = new MethodSecurityDefFactory(guardDefFactory);
    }

    @Test
    public void testTypicalGuard() throws NoSuchMethodException {

        Method ruleProtectedMethod = SampleService.class.getMethod("ruleProtectedMethod");
        MethodSecurityDef guardDef = methodSecurityDefFactory.create(ruleProtectedMethod);
        Mockito.verify(guardDefFactory).create(Matchers.any(SampleGuard.class));

        Annotation guarguardAnontattion = guardDef.getGuards().iterator().next().getGuardAnontattion();
        Guard guardMarker = guardDef.getGuards().iterator().next().getGuard();

        Assert.assertNotNull(guarguardAnontattion);
        Assert.assertNotNull(guardMarker);

        Assert.assertEquals(SampleGuard.class, guarguardAnontattion.annotationType());
        Assert.assertEquals(SampleGuardRule.class, guardMarker.value());

    }

    @Test
    public void testOverridingGuardAttributes() throws NoSuchMethodException {
        Method ruleProtectedMethod = SampleService.class.getMethod("ruleProtectedMethodWithOverridenAttributes");
        MethodSecurityDef guardDef = methodSecurityDefFactory.create(ruleProtectedMethod);

        Assert.assertEquals(AlternateSampleGuardRule.class, guardDef.getGuards().iterator().next().getGuard().value());
    }

    @Test
    public void testTwoRules() throws NoSuchMethodException {
        Method ruleProtectedMethod = SampleService.class.getMethod("twoRules");
        MethodSecurityDef methodSecurityDef = methodSecurityDefFactory.create(ruleProtectedMethod);

        Assert.assertEquals(2, methodSecurityDef.getGuards().size());
    }

    @Test
    public void testComposedGuard() throws NoSuchMethodException {
        Method ruleProtectedMethod = SampleService.class.getMethod("composedRuleProtection");
        MethodSecurityDef methodSecurityDef = methodSecurityDefFactory.create(ruleProtectedMethod);

        Assert.assertEquals(1, methodSecurityDef.getGuards().size());
        Assert.assertEquals(1, methodSecurityDef.getGuards().iterator().next().getGuards().size());

        List<GuardDef<?>> guards = methodSecurityDef.getGuards().iterator().next().getGuards();
        Assert.assertEquals(RolesAllowed.class,
                guards.iterator().next().getGuardAnontattion().annotationType());

    }


}

package org.sentinel;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.sentinel.def.GuardDefFactory;
import org.sentinel.def.MethodSecurityDefFactory;
import org.sentinel.def.GuardDef;
import org.sentinel.def.MethodSecurityDef;
import org.sentinel.guards.sample.SampleService;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Date: 11.12.11 20:42
 *
 * @author: Tomasz Krzyżak
 */
@RunWith(MockitoJUnitRunner.class)
public class MethodSecurityDefTest {

    private MethodSecurityDefFactory methodSecurityDefFactory;

    @Mock
    private GuardDefFactory guardDefFactory;

    @Before
    public void before() {
        methodSecurityDefFactory = new MethodSecurityDefFactory(guardDefFactory);

    }

    @Test
    public void testExecute() throws NoSuchMethodException {
        GuardDef guardDef = Mockito.mock(GuardDef.class);
        GuardDef[] guards = new GuardDef[]{guardDef};

        Method ruleProtectedMethod = SampleService.class.getMethod("ruleProtectedMethod");
        Mockito.doReturn(Arrays.asList(guards)).when(guardDefFactory).createGuardDefs(Matchers.eq(ruleProtectedMethod.getAnnotations()));
        Mockito.doReturn(true).when(guardDef).isExecutePermitted();

        MethodSecurityDef methodSecurityDef = methodSecurityDefFactory.create(ruleProtectedMethod);
        Mockito.verify(guardDefFactory).createGuardDefs(Matchers.eq(ruleProtectedMethod.getAnnotations()));
        Assert.assertTrue(methodSecurityDef.isExecutePermitted());

        Mockito.verify(guardDef).isExecutePermitted();
    }

}

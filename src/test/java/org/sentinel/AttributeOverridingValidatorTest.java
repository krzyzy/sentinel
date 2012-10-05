package org.sentinel;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.sentinel.AttributeOverridingValidator;
import org.sentinel.InvalidOverrideException;
import org.sentinel.Sentinel;
import org.sentinel.def.GuardDefFactory;
import org.sentinel.def.MethodSecurityDefFactory;
import org.sentinel.def.MethodSecurityDef;
import org.sentinel.guards.sample.SampleService;

import java.lang.reflect.Method;

/**
 * Date: 06.12.11 18:53
 *
 * @author: Tomasz Krzyżak
 */
@RunWith(MockitoJUnitRunner.class)
public class AttributeOverridingValidatorTest {

    private MethodSecurityDefFactory methodSecurityDefFactory;
    private GuardDefFactory guardDefFactory = new GuardDefFactory(Sentinel.create());

    @Before
    public void before() {
        new AttributeOverridingValidator();
        methodSecurityDefFactory = new MethodSecurityDefFactory(guardDefFactory);
    }

    @Test
    public void testInvalidOverrides() throws NoSuchMethodException {
        Method ruleProtectedMethod = SampleService.class.getMethod("ruleWithWrongOverrides");
        try {
            MethodSecurityDef guardDef = methodSecurityDefFactory.create(ruleProtectedMethod);
            Assert.fail("should fail because method is annotated by annotation with invalid overrides");
        } catch (InvalidOverrideException e) {
            e.printStackTrace(System.out);
        }
    }
}

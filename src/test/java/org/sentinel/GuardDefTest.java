package org.sentinel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.sentinel.def.GuardDef;

import static org.mockito.Matchers.same;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

/**
 * Date: 23.12.11 23:17
 *
 * @author: Tomasz Krzyżak
 */
@RunWith(MockitoJUnitRunner.class)
public class GuardDefTest extends BaseSentinelTest {


    @Spy
    Sentinel sentinel = Sentinel.create();

    @Mock
    UnderTestGuardRule guardRule;

    GuardDef<UnderTestAnnotation> guardDef;

    @Before
    public void before() {
        guardDef = new GuardDef<UnderTestAnnotation>(guard, guardAnnotation, sentinel);

        doReturn(guardRule).when(sentinel).createGuardRule(same(guardDef));
    }

    @Test
    public void testGetRuleClass() {
        Assert.assertEquals(UnderTestGuardRule.class, guardDef.getRuleClass());
    }

    @Test
    public void testGuardRuleCreated() {
        guardDef.isExecutePermitted();

        verify(sentinel).createGuardRule(same(guardDef));
    }

    @Test
    public void testContextCreatedWhenExecuting() {


        guardDef.isExecutePermitted();

        verify(sentinel).createContext(same(guard), same(guardAnnotation));
    }

    @Test
    public void testGuardRuleExecuted() {


        guardDef.isExecutePermitted();

        verify(guardRule).isExecutePermitted(Matchers.<SentinelContext<UnderTestAnnotation>>any());
    }


}

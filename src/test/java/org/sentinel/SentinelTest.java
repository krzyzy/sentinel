package org.sentinel;

import org.jboss.weld.bootstrap.api.helpers.SimpleServiceRegistry;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.metadata.TypeStore;
import org.jboss.weld.resources.ClassTransformer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.sentinel.def.GuardDef;

/**
 * Date: 24.12.11 22:48
 *
 * @author: Tomasz Krzyżak
 */
@RunWith(MockitoJUnitRunner.class)
public class SentinelTest extends BaseSentinelTest {

    Sentinel sentinel;

    GuardDef<UnderTestAnnotation> guardDef;

    @Before
    public void before() {
        SimpleServiceRegistry serviceRegistry = new SimpleServiceRegistry();
        serviceRegistry.add(ClassTransformer.class, new ClassTransformer(new TypeStore()));
        sentinel = Mockito.spy(new Sentinel(BeanManagerImpl.newRootManager("id", serviceRegistry)));
        guardDef = new GuardDef<UnderTestAnnotation>(guard, guardAnnotation, sentinel);
    }


    @Test
    public void testCreateContext() {
        SentinelContext<UnderTestAnnotation> context = sentinel.createContext(guard, guardAnnotation);

        Assert.assertSame(context.getGuardAnnotation(), guardAnnotation);
        Assert.assertSame(context.getGuard(), guard);
        Assert.assertSame(context.getSentinel(), sentinel);
    }

    @Test
    public void testCreateGuardRule() {
        Assert.assertNotNull(sentinel.createGuardRule(guardDef));
    }
}

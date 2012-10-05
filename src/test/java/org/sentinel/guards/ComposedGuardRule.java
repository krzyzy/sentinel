package org.sentinel.guards;

import org.sentinel.GuardRule;
import org.sentinel.SentinelContext;

/**
 * Date: 07.12.11 17:27
 *
 * @author: Tomasz Krzyżak
 */
public class ComposedGuardRule implements GuardRule<ComposedGuard> {
    public boolean isExecutePermitted(SentinelContext<ComposedGuard> context) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

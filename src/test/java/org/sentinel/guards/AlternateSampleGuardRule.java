package org.sentinel.guards;

import org.sentinel.GuardRule;
import org.sentinel.SentinelContext;

/**
 * Date: 06.12.11 17:54
 *
 * @author: Tomasz Krzyżak
 */
public class AlternateSampleGuardRule implements GuardRule<SampleGuardRuleWithOverrides> {
    public boolean isExecutePermitted( SentinelContext<SampleGuardRuleWithOverrides> context) {
        return false;
    }
}

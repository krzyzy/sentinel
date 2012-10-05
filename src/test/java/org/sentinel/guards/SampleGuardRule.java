package org.sentinel.guards;

import org.sentinel.GuardRule;
import org.sentinel.SentinelContext;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.math.BigDecimal;

/**
 * Date: 01.12.11 23:35
 *
 * @author: Tomasz Krzyżak
 */
public class SampleGuardRule implements GuardRule<SampleGuard> {

    @Inject
    @Default
    Integer customerId;

    @Inject
    @Default
    Number someNumber;

    @Inject
    @Default
    BigDecimal decimall;

    public boolean isExecutePermitted(SentinelContext<SampleGuard> context) {
        if (customerId == null)
            throw new IllegalStateException("customerId not injected");
        if (someNumber == null)
            throw new IllegalStateException("someNumber not injected");

        if (decimall == null)
            throw new IllegalStateException("decimal not injected");

        return Integer.valueOf(1).equals(customerId);
    }
}

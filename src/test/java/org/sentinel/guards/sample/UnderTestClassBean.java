package org.sentinel.guards.sample;

import org.sentinel.ProtectedBySentinel;
import org.sentinel.guards.SampleGuard;

import javax.enterprise.inject.Default;
import java.math.BigDecimal;

@ProtectedBySentinel
public class UnderTestClassBean {

    @SampleGuard
    public void someMethod(@Default Integer customerId, @Default Number someNumber, @Default BigDecimal decimal) {

    }

}
package org.sentinel;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Date: 06.12.11 19:00
 *
 * @author: Tomasz Krzyżak
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({AttributeOverridingValidatorTest.class,
        DefaultMethodSecurityDefFactoryTest.class, MethodSecurityDefTest.class, RolesAllowedRuleTest.class,
        GuardDefTest.class, SentinelTest.class})
public class SentinelSuite {
}

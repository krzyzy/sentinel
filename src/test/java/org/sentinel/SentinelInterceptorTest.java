package org.sentinel;

import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sentinel.Sentinel;
import org.sentinel.SentinelExtension;
import org.sentinel.def.MethodSecurityDefFactory;
import org.sentinel.guards.SampleGuard;
import org.sentinel.guards.sample.UnderTestClassBean;
import org.sentinel.scope.BeforeInvocationContextManager;

import javax.inject.Inject;
import java.math.BigDecimal;

/**
 * Date: 25.12.11 23:20
 *
 * @author: Tomasz Krzyżak
 */
@RunWith(Arquillian.class)
public class SentinelInterceptorTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class, "test.jar")
                .addClass(UnderTestClassBean.class)
                .addPackage(Sentinel.class.getPackage())
                .addPackage(SampleGuard.class.getPackage())
                .addPackage(MethodSecurityDefFactory.class.getPackage())
                .addPackage(BeforeInvocationContextManager.class.getPackage())
                .addAsManifestResource(new StringAsset(SentinelExtension.class.getCanonicalName()), "services/javax.enterprise.inject.spi.Extension")
                .addAsManifestResource(new StringAsset("<beans\n" +
                        "   xmlns=\"http://java.sun.com/xml/ns/javaee\"\n" +
                        "   xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                        "   xsi:schemaLocation=\"\n" +
                        "      http://java.sun.com/xml/ns/javaee\n" +
                        "      http://java.sun.com/xml/ns/javaee/beans_1_0.xsd\">\n" +
                        "   <interceptors>\n" +
                        "      <class>org.sentinel.scope.ContextSetupInterceptor</class>\n" +
                        "      <class>org.sentinel.scope.SentinelInterceptor</class>\n" +
                        "   </interceptors>\n" +
                        "</beans>"), ArchivePaths.create("beans.xml"));
    }

    @Inject
    UnderTestClassBean underTestClass;


    @Test
    public void testSentinelInjected() {
        Assert.assertNotNull(underTestClass);
        underTestClass.someMethod(1, 1.2d, BigDecimal.ONE);

        underTestClass.someMethod(2, 2.1d, BigDecimal.ONE);
    }

}

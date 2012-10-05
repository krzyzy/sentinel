package org.sentinel.guards.sample;

import org.sentinel.guards.*;
import org.sentinel.guards.operation.*;
import org.sentinel.operations.ResourceInstanceId;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Date: 26.11.11 14:14
 *
 * @author: Tomasz Krzyżak
 */
public class SampleService {

    @SampleGuard
    public void ruleProtectedMethod() {
    }

    @SampleGuardWithInvalidOverrides("sdfsdf")
    public void ruleWithWrongOverrides() {
    }

    @SampleGuardRuleWithOverrides(AlternateSampleGuardRule.class)
    public void ruleProtectedMethodWithOverridenAttributes() {
    }

    @SampleGuard
    @RolesAllowed({"rol1"})
    public void twoRules() {
    }

    @RolesAllowed({"role1, role2"})
    public void roleProtectedMethod() {

    }

    @ComposedGuard
    public void composedRuleProtection(){

    }

    @SampleOperation
    public void sampleMethod(SampleProtectedResource resurce) {

    }

    @Create(SampleProtectedResource.class)
    public void createMethod(String value1, String value2, Integer value3) {

    }

    @Read(SampleProtectedResource.class)
    public Collection<SampleProtectedResource> getAll() {
        return Collections.emptyList();
    }

    @Read(SampleProtectedResource.class)
    public SampleProtectedResource get(@ResourceInstanceId String id) {
        return null;
    }


    @Update(SampleProtectedResource.class)
    public void update(@ResourceInstanceId Integer resourceInstanceId, Map<String, Object> fieldValues) {

    }

    @Update(SampleProtectedResource.class)
    public void update(SampleProtectedResource instance) {

    }

    @Delete(SampleProtectedResource.class)
    public SampleProtectedResource delete(@ResourceInstanceId String id) {
        return null;
    }


}

package org.sentinel;

public class OperationRule implements GuardRule<Operation> {

    public boolean isExecutePermitted(SentinelContext<Operation> context) {
        throw new RuntimeException("not yet implemented");
    }
}
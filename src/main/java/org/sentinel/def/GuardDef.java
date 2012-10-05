package org.sentinel.def;

import org.sentinel.Guard;
import org.sentinel.GuardRule;
import org.sentinel.Sentinel;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Date: 06.12.11 23:33
 *
 * @author: Tomasz Krzyżak
 */
public class GuardDef<T extends Annotation> {

    private Guard guard;
    private T guardAnnotation;
    private Sentinel sentinel;
    private List<GuardDef<?>> guards = new LinkedList<GuardDef<?>>();
    private Class<? extends GuardRule<T>> ruleClass;

    public GuardDef(Guard guard, T guardAnnotation, Sentinel sentinel) {
        this.guard = guard;
        this.guardAnnotation = guardAnnotation;
        this.sentinel = sentinel;
        this.ruleClass = (Class<? extends GuardRule<T>>) this.guard.value();
    }

    public Sentinel getSentinel() {
        return sentinel;
    }

    public Guard getGuard() {
        return guard;
    }

    public T getGuardAnontattion() {
        return guardAnnotation;
    }

    public List<GuardDef<?>> getGuards() {
        return Collections.unmodifiableList(guards);
    }

    void setGuards(List<GuardDef<?>> guardDefs) {
        this.guards.clear();
        this.guards.addAll(guardDefs);
    }

    public boolean isExecutePermitted() {
        return sentinel.createGuardRule(this).isExecutePermitted(sentinel.createContext(guard, guardAnnotation));
    }

    public Class<? extends GuardRule<T>> getRuleClass() {
        return ruleClass;
    }
}

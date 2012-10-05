package org.sentinel.def;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Date: 01.12.11 23:48
 *
 * @author: Tomasz Krzyżak
 */
public class MethodSecurityDef implements RulesInvoker{

    private List<GuardDef<?>> guards = new LinkedList<GuardDef<?>>();

    public MethodSecurityDef(List<GuardDef<?>> guardDefs) {
        guards.addAll(guardDefs);
    }

    public List<GuardDef<?>> getGuards() {
        return Collections.unmodifiableList(guards);
    }

    public boolean isExecutePermitted() {
        for (GuardDef g : getGuards()) {
            if (!g.isExecutePermitted())
                return false;
        }
        return true;
    }


}

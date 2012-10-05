package org.sentinel.scope;

import org.jboss.weld.context.AbstractThreadLocalMapContext;
import org.jboss.weld.context.api.helpers.ConcurrentHashMapBeanStore;

public class BeforeInvocationContext extends AbstractThreadLocalMapContext {

    private static BeforeInvocationContext instance = new BeforeInvocationContext();

    /**
     * Constructor
     */
    private BeforeInvocationContext() {
        super(BeforeInvocationScope.class);
    }

    @Override
    public String toString() {
        String active = isActive() ? "Active " : "Inactive ";
        String beanStoreInfo = getBeanStore() == null ? "" : getBeanStore().toString();
        return active + "request context " + beanStoreInfo;
    }

    public void activate() {
        setActive(true);
        setBeanStore(new ConcurrentHashMapBeanStore());
    }

    public void deActivate() {
        destroy();
        setActive(false);
        setBeanStore(null);
    }

    public void reActivate() {
        deActivate();
        activate();
    }

    public static BeforeInvocationContext getInstance() {
        return instance;
    }

}
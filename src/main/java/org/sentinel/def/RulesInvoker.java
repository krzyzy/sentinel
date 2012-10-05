package org.sentinel.def;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: jan
 * Date: 05.01.12
 * Time: 21:40
 * To change this template use File | Settings | File Templates.
 */
public interface RulesInvoker extends Serializable {

    boolean isExecutePermitted();
}

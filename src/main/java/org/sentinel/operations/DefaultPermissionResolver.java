package org.sentinel.operations;

/**
 * Default implementation of PermissionResovler. Uses permissions store and
 * <ul>
 * <li>requested operation</li>
 * <li>resource type</li>
 * <li>resource instance id</li>
 * </ul>
 * <p/>
 * to resolve use permission.
 *
 * @author: Tomasz Krzyżak
 * Date: 26.11.11 14:35
 */
public class DefaultPermissionResolver implements PermissionResolver {

    //@Inject
    @ResourceType
    private Class<?> resourceType;

    //@Inject
    @ResourceInstanceId
    private Object resourceInstanceId;

    public boolean isPermissionGranted() {
        throw new RuntimeException("not yet implemented");
    }

}

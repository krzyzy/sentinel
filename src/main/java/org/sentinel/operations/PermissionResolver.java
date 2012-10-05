package org.sentinel.operations;

/**
 * Used to check whether subject invoking some method is granted the right to do so.
 * There is one default implementation ({@pl.accuratus.sentinel.guards.operations.DefaultPermissionResolver}).
 * Following values can be injected to instance of PermissionResolver:
 * <p/>
 * <ul>
 * <li>resource type by annotating field or property of typ Class<?> with {@pl.accuratus.sentinel.guards.operations.ResourceType}
 * and {@javax.inject.Inject}</li>
 * <li>{@pl.accuratus.sentinel.guards.Operation} annotation instance by annotating field or property of type
 * {@pl.accuratus.sentinel.guards.Operation} with {@javax.inject.Inject}</li>
 * <li>operation annotation instance by annotating by annotating field of property of type of operation annotation of
 * {@java.lang.annotation.Annotation} with {@javax.inject.Inject}</li>
 * <li>resource instance is (if available ) by annotating field of property with {@pl.accuratus.sentinel.guards.operations.ResourceInstanceId}</li>
 * </ul>
 *
 * @author: Tomasz Krzyżak
 * <p/>
 * Date: 25.11.11 23:25
 * @see DefaultPermissionResolver
 */
public interface PermissionResolver {

    public boolean isPermissionGranted();

}

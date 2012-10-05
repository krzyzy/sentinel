package org.sentinel;

import org.sentinel.operations.DefaultPermissionResolver;
import org.sentinel.operations.PermissionResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * marker interface used to identify operation annotations. Anyone can define operation. An operation annotation:
 * <ul>
 * <li>is annotated with {@Operation}, {@Retention(RetentionPolicy.RUNTIME)}</li>
 * <li>can have attributes</li>
 * <li>can define members and annotate them with {@pl.accuratus.sentinel.OverridesAttribute} to override composing
 * operations or
 * {@pl.accuratus.sentinel.guards.Operation} members</li>
 * </ul>
 * <p/>
 * <p>For example:
 * <ul><li>To specify operation "create" performed on resource type foo.bar.SomeResourceClass: following annotation
 * needs to be created and method which actually performs this operation needs to be annotated
 * <pre>
 *  &#064;Operation(value = "create", resourceType=foo.bar.SomeResourceClass.class)
 *  &#064;Retention(RetentionPolicy.RUNTIME)
 *  &#064;Target(ElementType.METHOD)
 *  public @interface Creates {
 *
 *  }
 *
 *  &#064;Stateless
 *  public class BeanClass {
 *      &#064;Creates
 *      public void someMethod(int attribute1, String attribute2){
 *      }
 *  }
 *
 *      </pre>
 * </li>
 * <li>To specify operation "create" performed on <b>any resource</b>: following annotation
 * needs to be created and method which actually performs this operation needs to be annotated
 * <pre>
 *  &#064;Operation(value = "create")
 *  &#064;Retention(RetentionPolicy.RUNTIME)
 *  &#064;Target(ElementType.METHOD)
 *  public @interface Creates {
 *
 *      &#064;OverridesAttribute(guard = Operation.class, name = "resourceType") Class<?> value();
 *
 *  }
 *
 *  &#064;Stateless
 *  public class BeanClass {
 *      &#064;Creates(foo.bar.SomeResourceClass.class)
 *      public void someMethod(int attribute1, String attribute2){
 *      }
 *  }
 *
 *      </pre>
 * </li>
 * </ul>
 * </p>
 *
 * @author: Tomasz Krzyżak
 * <p/>
 * Date: 25.11.11 23:25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Operation {


    /**
     * name of permission. This name is used by permission {@PermissionResolver}s to identify operation
     *
     * @return name of permission
     */
    String value();

    /**
     * class of resource on which operation is performed. In most cases this will be overriden by
     * {@pl.accuratus.sentinel.OverridesAttribute} in operation annotations
     *
     * @return
     */
    Class<?> resourceType() default Void.class;

    /**
     * permission permissionResolver.
     *
     * @return
     */
    Class<? extends PermissionResolver> permissionResolver() default DefaultPermissionResolver.class;

}

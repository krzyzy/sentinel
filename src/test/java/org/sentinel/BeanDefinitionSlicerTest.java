package org.sentinel;

import junit.framework.Assert;
import org.jboss.weld.util.reflection.HierarchyDiscovery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.sentinel.Sentinel;
import org.sentinel.scope.BeanDefinitionSlicer;

import javax.enterprise.inject.Default;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: jan
 * Date: 27.01.12
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class BeanDefinitionSlicerTest {

    public static class IntegerList extends LinkedList<Integer> {

    }

    public static class IntegerSet extends HashSet<Integer> {

    }

    @Test
    public void testAddOneBeanDefinition() {
        BeanDefinitionSlicer slicer = new BeanDefinitionSlicer();

        slicer.addBean(Integer.class, Collections.<Annotation>emptySet(), new HierarchyDiscovery(Integer.class).getTypeClosure());
        Assert.assertEquals(1, slicer.getSlices().size());
    }

    @Test
    public void testAnnotationEquals() {
        Assert.assertTrue("should be equal", Sentinel.qualifierEquals(new Default() {
                                                                          public Class<? extends Annotation> annotationType() {
                                                                              return Default.class;
                                                                          }
                                                                      }, new Default() {
                                                                          public Class<? extends Annotation> annotationType() {
                                                                              return Default.class;
                                                                          }
                                                                      }
        ));
    }

    @Test
    public void testSliceTwoSameBeans() {
        BeanDefinitionSlicer slicer = new BeanDefinitionSlicer();
        slicer.addBean(Integer.class, Collections.<Annotation>emptySet(), new HierarchyDiscovery(Integer.class).getTypeClosure());
        slicer.addBean(Integer.class, Collections.<Annotation>emptySet(), new HierarchyDiscovery(Integer.class).getTypeClosure());

        Assert.assertEquals(1, slicer.getSlices().size());
    }

    @Test
    public void testSliceClassAndSuperClass() {
        BeanDefinitionSlicer slicer = new BeanDefinitionSlicer();
        slicer.addBean(Integer.class, Collections.<Annotation>emptySet(), new HierarchyDiscovery(Integer.class).getTypeClosure());
        slicer.addBean(Number.class, Collections.<Annotation>emptySet(), new HierarchyDiscovery(Number.class).getTypeClosure());

        Assert.assertEquals(4, slicer.getSlices().size());


        slicer = new BeanDefinitionSlicer();
        slicer.addBean(Number.class, Collections.<Annotation>emptySet(), new HierarchyDiscovery(Number.class).getTypeClosure());
        slicer.addBean(Integer.class, Collections.<Annotation>emptySet(), new HierarchyDiscovery(Integer.class).getTypeClosure());

        Assert.assertEquals(4, slicer.getSlices().size());
    }


    @Test
    public void testSliceThreeDefinitions() {
        BeanDefinitionSlicer slicer = new BeanDefinitionSlicer();
        slicer.addBean(Integer.class, Collections.<Annotation>emptySet(), new HierarchyDiscovery(Integer.class).getTypeClosure());
        slicer.addBean(Long.class, Collections.<Annotation>emptySet(), new HierarchyDiscovery(Long.class).getTypeClosure());
        slicer.addBean(Float.class, Collections.<Annotation>emptySet(), new HierarchyDiscovery(Float.class).getTypeClosure());
        Assert.assertEquals(6, slicer.getSlices().size());

    }

    @Test
    public void testComplexTwoDefinitions() {
        BeanDefinitionSlicer slicer = new BeanDefinitionSlicer();

        slicer.addBean(IntegerList.class, Collections.<Annotation>emptySet(), new HierarchyDiscovery(IntegerList.class).getTypeClosure());
        slicer.addBean(IntegerSet.class, Collections.<Annotation>emptySet(), new HierarchyDiscovery(IntegerSet.class).getTypeClosure());
    }

}

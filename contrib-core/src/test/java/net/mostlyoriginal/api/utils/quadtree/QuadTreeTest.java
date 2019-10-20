package net.mostlyoriginal.api.utils.quadtree;

import com.artemis.utils.IntBag;
import net.mostlyoriginal.api.utils.QuadTree;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link net.mostlyoriginal.api.utils.QuadTree}
 *
 * @author Piotr-J
 */
public class QuadTreeTest {
    @Test
    public void get_inexact_test() {
        IntBag fill = new IntBag();
        QuadTree tree = new QuadTree(-8, -8, 8, 8, 1, 8);
        fill.clear();
        tree.get(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 0);

        tree.insert(1, -6, -6, 2, 2); // fully outside test region
        fill.clear();
        tree.get(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 1);

        tree.insert(2, 6, -6, 2, 2); // fully outside test region
        tree.insert(3, -2, 2, 2, 2); // fully inside test region
        tree.insert(4, 2, 2, 2, 2); // overlaps test region
        tree.get(fill, -2.5f, -2.5f, 5, 5);
        fill.clear();
        tree.get(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 4);

        // move inside test region
        tree.update(1, -2, -2, 2, 2);
        fill.clear();
        tree.get(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 4);

        // move outside test region
        tree.update(1, -6, -6, 2, 2);
        fill.clear();
        tree.get(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 4);

        // remove from outside
        tree.remove(1);
        fill.clear();
        tree.get(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 3);

        // remove overlapping
        tree.remove(3);
        fill.clear();
        tree.get(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 2);

        // remove inside
        tree.remove(4);
        fill.clear();
        tree.get(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 1);

        tree.remove(2);
        fill.clear();
        tree.get(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 0);
    }

    @Test
    public void get_exact_test() {
        IntBag fill = new IntBag();
        QuadTree tree = new QuadTree(-8, -8, 8, 8, 1, 8);
        fill.clear();
        tree.getExact(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 0);

        tree.insert(1, -6, -6, 2, 2); // fully outside test region
        fill.clear();
        tree.getExact(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 0);

        tree.insert(2, 6, -6, 2, 2); // fully outside test region
        tree.insert(3, -2, 2, 2, 2); // fully inside test region
        tree.insert(4, 2, 2, 2, 2); // overlaps test region
        tree.get(fill, -2.5f, -2.5f, 5, 5);
        fill.clear();
        tree.getExact(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 2);

        // move inside test region
        tree.update(1, -2, -2, 2, 2);
        fill.clear();
        tree.getExact(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 3);

        // move outside test region
        tree.update(1, -6, -6, 2, 2);
        fill.clear();
        tree.getExact(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 2);

        // remove from outside
        tree.remove(1);
        fill.clear();
        tree.getExact(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 2);

        // remove overlapping
        tree.remove(3);
        fill.clear();
        tree.getExact(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 1);

        // remove inside
        tree.remove(4);
        fill.clear();
        tree.getExact(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 0);

        tree.remove(2);
        fill.clear();
        tree.getExact(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 0);
    }


    @Test
    public void complex_get_test() {
        IntBag fill = new IntBag();
        QuadTree tree = new QuadTree(-8, -8, 8, 8, 1, 8);
        fill.clear();
        tree.get(fill, -3, -3, 6, 6);
        Assert.assertEquals(fill.size(), 0);

        // all outside
        tree.insert(1, -6, -6, 2, 2);
        tree.insert(2, 4, -6, 2, 2);
        tree.insert(3, -6, 4, 2, 2);
        tree.insert(4, 4, 4, 2, 2);

        tree.insert(5, -1, -1, 2, 2); // center
        tree.insert(6, -4, -4, 2, 2); // fully inside
        tree.insert(7, 2, 2, 2, 2); // fully inside
        tree.insert(8, -4, -4, 2, 2); // fully inside

    }
    
    @Test
    public void inexact_point_single_entity_test() {
        IntBag fill = new IntBag();
        QuadTree tree = new QuadTree(-8, -8, 8, 8, 1, 8);
        fill.clear();
        tree.get(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 0);
        
        tree.insert(1, -1, -1, 2, 2);
        
        fill.clear();
        tree.get(fill, 0, 0);
        Assert.assertEquals(fill.size(), 1);
    }

    @Test
    public void inexact_point_test() {
        IntBag fill = new IntBag();
        QuadTree tree = new QuadTree(-8, -8, 8, 8, 1, 8);
        fill.clear();
        tree.get(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 0);

        tree.insert(1, -1, -1, 2, 2);
        tree.insert(2, -1, -1, 2, 2);

        fill.clear();
        tree.get(fill, 0, 0);
        Assert.assertEquals(fill.size(), 2);
    }

    @Test
    public void exact_point_single_entity_test() {
        IntBag fill = new IntBag();
        QuadTree tree = new QuadTree(-8, -8, 8, 8, 1, 8);
        fill.clear();
        tree.get(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 0);

        tree.insert(1, -1, -1, 2, 2);

        fill.clear();
        tree.getExact(fill, 0, 0);
        Assert.assertEquals(fill.size(), 1);
    }

    @Test
    public void exact_point_test() {
        IntBag fill = new IntBag();
        QuadTree tree = new QuadTree(-8, -8, 8, 8, 1, 8);
        fill.clear();
        tree.get(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 0);

        tree.insert(1, -1, -1, 2, 2);
        tree.insert(2, -1, -1, 2, 2);

        fill.clear();
        tree.getExact(fill, 0, 0);
        Assert.assertEquals(fill.size(), 2);
    }

    @Test
    public void next_flag_test() {
        QuadTree tree = new QuadTree(-8, -8, 8, 8, 1, 8);
        
        Assert.assertEquals(1L, tree.nextFlag());
        Assert.assertEquals(2L, tree.nextFlag());
        Assert.assertEquals(4L, tree.nextFlag());
        Assert.assertEquals(8L, tree.nextFlag());
    }

    @Test
    public void flags_inexact_test() {
        IntBag fill = new IntBag();
        QuadTree tree = new QuadTree(-8, -8, 8, 8, 1, 8);
        fill.clear();
        tree.get(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 0);

        tree.insert(1, 0L, -1, -1, 2, 2);
        tree.insert(2, 1L, -1, -1, 2, 2);
        tree.insert(3, 2L, -1, -1, 2, 2);
        tree.insert(4, 2L, -1, -1, 2, 2);
        tree.insert(5, 3L, -1, -1, 2, 2);

        // 0 flag
        fill.clear();
        tree.get(fill, -2, -2, 2, 2, 0L);
        Assert.assertEquals(fill.size(), 5);

        // 1 flag
        fill.clear();
        tree.get(fill, -2, -2, 2, 2, 1L);
        Assert.assertEquals(fill.size(), 2);

        // 2 flag
        fill.clear();
        tree.get(fill, -2, -2, 2, 2, 2L);
        Assert.assertEquals(fill.size(), 3);
    }

    @Test
    public void flags_exact_test() {
        IntBag fill = new IntBag();
        QuadTree tree = new QuadTree(-8, -8, 8, 8, 1, 8);
        fill.clear();
        tree.getExact(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 0);

        tree.insert(1, 0L, -1, -1, 2, 2);
        tree.insert(2, 1L, -1, -1, 2, 2);
        tree.insert(3, 2L, -1, -1, 2, 2);
        tree.insert(4, 2L, -1, -1, 2, 2);
        tree.insert(5, 3L, -1, -1, 2, 2);

        // 0 flag
        fill.clear();
        tree.getExact(fill, -2, -2, 2, 2, 0L);
        Assert.assertEquals(fill.size(), 5);

        // 1 flag
        fill.clear();
        tree.getExact(fill, -2, -2, 2, 2, 1L);
        Assert.assertEquals(fill.size(), 2);

        // 2 flag
        fill.clear();
        tree.getExact(fill, -2, -2, 2, 2, 2L);
        Assert.assertEquals(fill.size(), 3);
    }

    @Test
    public void flags_inexact_point_test() {
        IntBag fill = new IntBag();
        QuadTree tree = new QuadTree(-8, -8, 8, 8, 1, 8);
        fill.clear();
        tree.get(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 0);

        tree.insert(1, 0L, -1, -1, 2, 2);
        tree.insert(2, 1L, -1, -1, 2, 2);
        tree.insert(3, 2L, -1, -1, 2, 2);
        tree.insert(4, 2L, -1, -1, 2, 2);
        tree.insert(5, 3L, -1, -1, 2, 2);

        // 0 flag
        fill.clear();
        tree.get(fill, 0, 0, 0L);
        Assert.assertEquals(fill.size(), 5);

        // 1 flag
        fill.clear();
        tree.get(fill, 0, 0, 1L);
        Assert.assertEquals(fill.size(), 2);

        // 2 flag
        fill.clear();
        tree.get(fill, 0, 0, 2L);
        Assert.assertEquals(fill.size(), 3);
    }

    @Test
    public void flags_exact_point_test() {
        IntBag fill = new IntBag();
        QuadTree tree = new QuadTree(-8, -8, 8, 8, 1, 8);
        fill.clear();
        tree.getExact(fill, -2.5f, -2.5f, 5, 5);
        Assert.assertEquals(fill.size(), 0);

        tree.insert(1, 0L, -1, -1, 2, 2);
        tree.insert(2, 1L, -1, -1, 2, 2);
        tree.insert(3, 2L, -1, -1, 2, 2);
        tree.insert(4, 2L, -1, -1, 2, 2);
        tree.insert(5, 3L, -1, -1, 2, 2);

        // 0 flag
        fill.clear();
        tree.getExact(fill, 0, 0, 0L);
        Assert.assertEquals(fill.size(), 5);

        // 1 flag
        fill.clear();
        tree.getExact(fill, 0, 0, 1L);
        Assert.assertEquals(fill.size(), 2);

        // 2 flag
        fill.clear();
        tree.getExact(fill, 0, 0, 2L);
        Assert.assertEquals(fill.size(), 3);
    }

}

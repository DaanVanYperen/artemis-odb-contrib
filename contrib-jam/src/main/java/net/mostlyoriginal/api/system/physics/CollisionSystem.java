package net.mostlyoriginal.api.system.physics;

/**
 * @author Daan van Yperen
 */

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import net.mostlyoriginal.api.component.basic.Bounds;
import net.mostlyoriginal.api.component.basic.Pos;

/**
 * Utility detect collisions
 *
 * @author Daan van Yperen
 * @todo generalize, daan! this is crap.
 */
@Wire
public class CollisionSystem extends BaseSystem {

    private ComponentMapper<Bounds> bm;
    private ComponentMapper<Pos> pm;

    public final boolean overlaps( final Entity a, final Entity b)
    {
        final Bounds b1 = bm.getSafe(a);
        final Pos p1 =  pm.getSafe(a);
        final Bounds b2 = bm.getSafe(b);
        final Pos p2 =  pm.getSafe(b);

        if ( b1==null || p1 ==null || b2==null || p2==null)
            return false;

        final float minx = p1.x + b1.minx;
        final float miny = p1.y + b1.miny;
        final float maxx = p1.x + b1.maxx;
        final float maxy = p1.y + b1.maxy;

        final float bminx = p2.x + b2.minx;
        final float bminy = p2.y + b2.miny;
        final float bmaxx = p2.x + b2.maxx;
        final float bmaxy = p2.y + b2.maxy;

        return
                !(minx > bmaxx || maxx < bminx ||
                  miny > bmaxy || maxy < bminy );
    }

    @Override
    protected void processSystem() {

    }
}

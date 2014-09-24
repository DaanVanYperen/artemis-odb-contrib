package net.mostlyoriginal.api.utils;

import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.math.Vector2;
import net.mostlyoriginal.api.component.basic.Pos;

/**
 * @author Daan van Yperen
 */
public class EntityUtil {

    private static Vector2 tmp = new Vector2();

    public static float distance2( final Entity a, final Entity b)
    {
        final Pos pa = (Pos)a.getComponent(Pos.class);
        final Pos pb = (Pos)b.getComponent(Pos.class);

        return tmp.set(pa.x, pa.y).dst2(pb.x, pb.y);
    }

    public static float angle( final Entity a, final Entity b)
    {
        final Pos pa = (Pos)a.getComponent(Pos.class);
        final Pos pb = (Pos)b.getComponent(Pos.class);

        return tmp.set(pb.x, pb.y).sub(pa.x, pa.y).angle();
   }

    public static float distance( final Entity a, final Entity b)
    {
        final Pos pa = (Pos)a.getComponent(Pos.class);
        final Pos pb = (Pos)b.getComponent(Pos.class);

        return tmp.set(pa.x, pa.y).dst(pb.x, pb.y);
    }

    /** Safely delete all entities from bag, regardless if the bag changes. */
    public static void safeDeleteAll(final ImmutableBag<Entity> entities1) {
        final Bag<Entity> entities = new Bag<>();
        entities.addAll(entities1);
        for (int i = 0, s = entities.size(); i < s; i++) {
            entities.get(i).deleteFromWorld();
        }
    }
}
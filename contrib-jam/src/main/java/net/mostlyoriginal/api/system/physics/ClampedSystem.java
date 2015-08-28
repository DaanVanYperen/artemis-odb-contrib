package net.mostlyoriginal.api.system.physics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.MathUtils;
import net.mostlyoriginal.api.component.basic.Bounds;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.physics.Clamped;
import net.mostlyoriginal.api.component.physics.Physics;

/**
 * Clamp entity rectangular region.
 *
 * Entities with bounds will be constrained to those bounds.
 *
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.physics.Clamped
 * @see net.mostlyoriginal.api.component.basic.Bounds
 */
@Wire(injectInherited = true)
public class ClampedSystem extends EntityProcessingSystem {

    private ComponentMapper<Pos> pm;
    private ComponentMapper<Bounds> bm;
    private ComponentMapper<Clamped> cm;
    private ComponentMapper<Physics> ym;


    /**
     * Creates a new EntityProcessingSystem.
     */
    @SuppressWarnings("unchecked")
    public ClampedSystem() {
        super(Aspect.all(Pos.class, Clamped.class));
    }

    @Override
    protected void process(Entity e) {
        final Pos pos = pm.get(e);
        final Bounds bounds = bm.has(e) ? bm.get(e) : Bounds.NONE;
        final Clamped clamped = cm.get(e);

        // determine valid viewport.
        final float wx1 = clamped.minx - bounds.minx;
        final float wx2 = clamped.maxx - bounds.maxx;
        final float wy1 = clamped.miny - bounds.miny;
        final float wy2 = clamped.maxy - bounds.maxy;

        // halt momentum if required.
        if ( ym.has(e))
        {
            Physics physics = ym.get(e);
            if ( physics.vx < 0 && pos.xy.x + physics.vx* world.delta <= wx1 ) physics.vx =0;
            if ( physics.vx > 0 && pos.xy.x + physics.vx* world.delta >= wx2 ) physics.vx =0;
            if ( physics.vy < 0 && pos.xy.y + physics.vy* world.delta <= wy1 ) physics.vy =0;
            if ( physics.vy > 0 && pos.xy.y + physics.vy* world.delta >= wy2 ) physics.vy =0;
        }

        // clamp coords
        pos.xy.x = MathUtils.clamp( pos.xy.x, wx1, wx2);
        pos.xy.y = MathUtils.clamp( pos.xy.y, wy1, wy2);
    }
}

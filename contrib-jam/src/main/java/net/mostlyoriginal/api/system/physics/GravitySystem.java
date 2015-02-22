package net.mostlyoriginal.api.system.physics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import net.mostlyoriginal.api.component.physics.Gravity;
import net.mostlyoriginal.api.component.physics.Physics;

/**
 * Applies gravity on Y axis to Entity.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.physics.Gravity
 */
@Wire
public class GravitySystem extends EntityProcessingSystem {

    public static final int GRAVITY_FACTOR = 50;

    ComponentMapper<Physics> pm;
    ComponentMapper<Gravity> gm;

    public GravitySystem() {
        super(Aspect.all(Gravity.class, Physics.class));
    }

    @Override
    protected void process(Entity e) {
        final Physics physics = pm.get(e);
        final Gravity gravity = gm.get(e);

        physics.vy += gravity.y * GRAVITY_FACTOR * world.delta;
        physics.vx += gravity.x * GRAVITY_FACTOR * world.delta;
    }
}

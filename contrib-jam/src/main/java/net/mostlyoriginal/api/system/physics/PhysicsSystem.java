package net.mostlyoriginal.api.system.physics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import net.mostlyoriginal.api.component.basic.Angle;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.map.MapWallSensor;
import net.mostlyoriginal.api.component.physics.Frozen;
import net.mostlyoriginal.api.component.physics.Physics;

/**
 * Handles basic physics.
 *
 * Applies velocity to position, and fudged friction to velocity.
 * Run after physics is clamped by controllers like the MapCollisionSystem.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.physics.Physics
 * @see net.mostlyoriginal.api.system.map.MapCollisionSystem
 */

@Wire
public class PhysicsSystem extends EntityProcessingSystem {

    public static final float AIR_FRICTION = 0.25f;
    public static final int FLOOR_FRICTION = 1;
    private ComponentMapper<Physics> ym;
    private ComponentMapper<Frozen> frozenm;
    private ComponentMapper<Pos> pm;
    private ComponentMapper<Angle> am;
    private ComponentMapper<MapWallSensor> wm;

    public PhysicsSystem() {
        super(Aspect.all(Physics.class, Pos.class));
    }

    private Vector2 vTmp = new Vector2();

    public void push(Entity entity, float rotation, float force) {
        if (ym.has(entity)) {
            vTmp.set(force, 0).setAngle(rotation);
            final Physics physics = ym.get(entity);
            physics.vx += vTmp.x;
            physics.vy += vTmp.y;
        }
    }

    public void clampVelocity(Entity entity, float minSpeed, float maxSpeed) {
        if (ym.has(entity)) {
            final Physics physics = ym.get(entity);
            clampVelocity(physics, minSpeed, maxSpeed);
        }
    }

    private void clampVelocity(Physics physics, float minSpeed, float maxSpeed) {
        vTmp.set(physics.vx, physics.vy).clamp(minSpeed,maxSpeed);
        physics.vx = vTmp.x;
        physics.vy = vTmp.y;
    }

    @Override
    protected void process(Entity e) {
        final Physics physics = ym.get(e);
        final Pos pos = pm.get(e);

        // don't process frozen entities.
        if ( frozenm.has(e)) return;

        if ( physics.maxVelocity < Float.MAX_VALUE)
            clampVelocity(e, 0, physics.maxVelocity);

        pos.xy.x += physics.vx * world.getDelta();
        pos.xy.y += physics.vy * world.getDelta();

        if ( physics.vr != 0 && am.has(e))
        {
            am.get(e).rotation += physics.vr * world.delta;
        }
        
        if (physics.friction != 0) {
            updateFudgeFriction(physics);
        }

    }

    /**
     * Not really friction, not really fudge!
     */
    private void updateFudgeFriction(Physics physics) {
        float adjustedFriction = physics.friction;

        if (Math.abs(physics.vx) > 0.005f) {
            physics.vx = physics.vx - (physics.vx * world.delta * adjustedFriction);
        } else {
            physics.vx = 0;
        }

        if (Math.abs(physics.vr) > 0.005f) {
            physics.vr = physics.vr - (physics.vr * world.delta * adjustedFriction);
        } else {
            physics.vr = 0;
        }

        if (Math.abs(physics.vy) > 0.005f) {
            physics.vy = physics.vy - (physics.vy * world.delta * adjustedFriction * 0.1f);
        } else {
            physics.vy = 0;
        }
    }
}

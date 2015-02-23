package net.mostlyoriginal.api.system.physics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.physics.Attached;
import net.mostlyoriginal.api.component.physics.Homing;
import net.mostlyoriginal.api.component.physics.Physics;
import net.mostlyoriginal.api.utils.EntityUtil;

/**
 * Accelerate entity towards target.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.physics.Homing
 */
@Wire
public class HomingSystem extends EntityProcessingSystem {

    ComponentMapper<Homing> hm;
    ComponentMapper<Pos> pm;
    ComponentMapper<Physics> ym;
    ComponentMapper<Attached> am;

    public HomingSystem() {
        super(Aspect.all(Homing.class, Pos.class));
    }

    private static final Vector2 tmp = new Vector2();

    @Override
    protected void process(Entity e) {

        final Homing homing = hm.get(e);
        if (homing.target != null && homing.target.valid()) {

            final float distance = EntityUtil.distance(e, homing.target.get());
            if (distance < homing.maxDistance) {

                final Pos myPos = pm.get(e);
                final Pos tPos = pm.get(homing.target.get());

                // vector of required traversal
                tmp.set(tPos.x, tPos.y).sub(myPos.x, myPos.y).scl(homing.speedFactor);

                if (ym.has(e)) {
                    Physics physics = ym.get(e);
                    physics.vx = tmp.x;
                    physics.vy = tmp.y;
                }

                if (am.has(e))
                {
                    Attached attached = am.get(e);
                    attached.slackX = tmp.x;
                    attached.slackY = tmp.y;
                }
            }

        } else homing.target = null;
    }
}

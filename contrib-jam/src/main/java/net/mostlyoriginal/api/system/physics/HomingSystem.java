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
	    final Entity homingTarget = homing.target.get();
	    if (homingTarget != null) {

            final float distance = EntityUtil.distance(e, homingTarget);
            if (distance < homing.maxDistance) {

                final Pos myPos = pm.get(e);
                final Pos tPos = pm.get(homingTarget);

                // vector of required traversal
                tmp.set(tPos.xy.x, tPos.xy.y).sub(myPos.xy.x, myPos.xy.y).scl(homing.speedFactor);

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

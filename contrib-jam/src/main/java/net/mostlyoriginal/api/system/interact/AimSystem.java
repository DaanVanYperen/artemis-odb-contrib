package net.mostlyoriginal.api.system.interact;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import net.mostlyoriginal.api.component.basic.Angle;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.interact.Aim;
import net.mostlyoriginal.api.utils.EntityUtil;

/**
 * Aim entity at target.
 *
 * Best performed after movement systems.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.interact.Aim
 */
@Wire
public class AimSystem extends IteratingSystem {

    private ComponentMapper<Aim> am;
    private ComponentMapper<Pos> pm;
    private ComponentMapper<Angle> anm;

    public AimSystem() {
        super(Aspect.all(Aim.class, Pos.class, Angle.class));
    }

    Vector2 vTmp = new Vector2();

    @Override
    protected void process(int e) {
        final Aim aim = am.get(e);

	    final int target = aim.at;
	    if (target != -1) {
            aimAt(e, target);
        }
    }

    public void aimAt(int e, int at) {

        final Pos pa = pm.get(e);
        final Pos pb = pm.get(at);

        anm.get(e).rotation = vTmp.set(pb.xy.x, pb.xy.y).sub(pa.xy.x, pa.xy.y).angle();
    }
}

package net.mostlyoriginal.api.system.interact;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import net.mostlyoriginal.api.component.basic.Angle;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.graphics.Anim;
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
public class AimSystem extends EntityProcessingSystem {

    private ComponentMapper<Aim> am;
    private ComponentMapper<Angle> anm;

    public AimSystem() {
        super(Aspect.getAspectForAll(Aim.class, Pos.class, Anim.class));
    }

    Vector2 vTmp = new Vector2();

    @Override
    protected void process(Entity e) {
        final Aim aim = am.get(e);

        if (aim.at.isActive()) {
            aimAt(e, aim.at.get());
        }
    }

    public void aimAt(Entity e, Entity at) {
        anm.get(e).rotation = EntityUtil.angle(e, at);
    }
}

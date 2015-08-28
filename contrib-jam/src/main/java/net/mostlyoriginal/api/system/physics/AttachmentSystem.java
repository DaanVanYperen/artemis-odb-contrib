package net.mostlyoriginal.api.system.physics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.physics.Attached;

/**
 * Move entities in line with attachments.
 *
 * @todo consider if we need to change what parent origin this works with.
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.physics.Attached
 */
@Wire
public class AttachmentSystem extends EntityProcessingSystem {

    private ComponentMapper<Pos> pm;
    private ComponentMapper<Attached> am;

    public AttachmentSystem() {
        super(Aspect.all(Pos.class, Attached.class));
    }

    Vector2 vTmp = new Vector2();

    @Override
    protected void process(Entity e) {
        final Attached attached = am.get(e);

	    final Entity parent = attached.parent.get();
	    if (parent != null) {

            // move attachment to absolute position, adjusted with slack.
            Pos pos = pm.get(e);
            Pos parPos = pm.get(parent);
            pos.xy.x = parPos.xy.x + attached.xo + attached.slackX;
            pos.xy.y = parPos.xy.y + attached.yo + attached.slackY;

            updateSlack(attached);
        } else {
            // parent gone? we gone!
            e.deleteFromWorld();
        }
    }

    /**
     * Apply force on joint, pushing the attached entity out of place.
     *
     * @param entity Entity to push
     * @param rotation Direction of force
     * @param force strength of force (don't factor in delta).
     */
    public void push(final Entity entity, float rotation, float force) {
        if (am.has(entity)) {
            push(am.get(entity), rotation, force);
        }
    }

    /**
     * Apply force on joint, pushing the attached entity out of place.
     *
     * @param attached Attached component of entity to push
     * @param rotation Direction of force
     * @param force strength of force (don't factor in delta).
     */
    public void push(final Attached attached, float rotation, float force) {
        vTmp.set(force, 0).rotate(rotation).add(attached.slackX, attached.slackY).clamp(0f, attached.maxSlack);
        attached.slackX = vTmp.x;
        attached.slackY = vTmp.y;
    }

    /**
     * Slack, like weapon recoil on the joint.
     *
     * @param attached
     */
    protected void updateSlack(final Attached attached) {

        float len = vTmp.set(attached.slackX, attached.slackY).len() - world.delta * attached.tension;
        if (len > 0) {
            vTmp.nor().scl(len);
        } else {
            vTmp.set(0, 0);
        }

        attached.slackX = vTmp.x;
        attached.slackY = vTmp.y;
    }
}

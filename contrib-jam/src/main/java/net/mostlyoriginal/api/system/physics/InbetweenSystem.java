package net.mostlyoriginal.api.system.physics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.physics.Inbetween;

/**
 * Place entity between two target entities.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.physics.Inbetween
 */
@Wire
public class InbetweenSystem extends EntityProcessingSystem {

    ComponentMapper<Inbetween> dm;
    ComponentMapper<Pos> pm;

    public InbetweenSystem() {
        super(Aspect.all(Inbetween.class, Pos.class));
    }


    Vector2 tmp = new Vector2();

    @Override
    protected void process(Entity e) {

        final Inbetween inbetween = dm.get(e);
        if ( !inbetween.a.valid() || !inbetween.b.valid() ) return;

        Pos pos1 = pm.get(inbetween.a.get());
        Pos pos2 = pm.get(inbetween.b.get());

        tmp.set(pos2.x + inbetween.bx, pos2.y + inbetween.by).sub(pos1.x + inbetween.ax, pos1.y + inbetween.ay).scl(inbetween.tween).clamp(0,inbetween.maxDistance).add(pos1.x + inbetween.ax,pos1.y + inbetween.ay);

        Pos pos = pm.get(e);
        pos.x = tmp.x;
        pos.y = tmp.y;
    }
}

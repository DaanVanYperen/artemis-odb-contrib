package net.mostlyoriginal.api.system.physics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.IteratingSystem;
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
public class InbetweenSystem extends IteratingSystem {

    ComponentMapper<Inbetween> dm;
    ComponentMapper<Pos> pm;

    public InbetweenSystem() {
        super(Aspect.all(Inbetween.class, Pos.class));
    }


    Vector2 tmp = new Vector2();

    @Override
    protected void process(int e) {

        final Inbetween inbetween = dm.get(e);

	    final int entityA = inbetween.a;
	    final int entityB = inbetween.b;
	    if ( entityA == -1 || entityB == -1 ) return;

        Pos pos1 = pm.get(entityA);
        Pos pos2 = pm.get(entityB);

        tmp.set(pos2.xy.x + inbetween.bx, pos2.xy.y + inbetween.by).sub(pos1.xy.x + inbetween.ax, pos1.xy.y + inbetween.ay).scl(inbetween.tween).clamp(0,inbetween.maxDistance).add(pos1.xy.x + inbetween.ax,pos1.xy.y + inbetween.ay);

        Pos pos = pm.get(e);
        pos.xy.x = tmp.x;
        pos.xy.y = tmp.y;
    }
}

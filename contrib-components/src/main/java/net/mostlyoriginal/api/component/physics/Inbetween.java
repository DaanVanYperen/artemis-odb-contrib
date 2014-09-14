package net.mostlyoriginal.api.component.physics;

import com.artemis.Component;
import net.mostlyoriginal.api.Depends;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.utils.reference.EntityReference;

/**
 * Entity positions itself somewhere between two target entities.
 *
 * @author Daan van Yperen
 */
@Depends(Pos.class)
public class Inbetween extends Component {

    public EntityReference a;
    public EntityReference b;
    /** Location in between point A and B 0.0 at a, 1.0 at b location */
    public float tween = 0.5f;
    /** Offset between A and B. */
    public float ax,ay;
    public float bx,by;
    /** Maximum pixel distance from point A. */
    public float maxDistance = Float.MAX_VALUE;

    public Inbetween(EntityReference a, EntityReference b) {
        this.a = a;
        this.b = b;
    }

    public Inbetween(EntityReference a, EntityReference b, float tween) {
        this.a = a;
        this.b = b;
        this.tween = tween;
    }
}

package net.mostlyoriginal.api.component.physics;

import com.artemis.Component;
import com.artemis.annotations.EntityId;

/**
 * Entity positions itself somewhere between two target entities.
 *
 * @author Daan van Yperen
 */
public class Inbetween extends Component {

    @EntityId public int a;
    @EntityId public int b;
    /** Location in between point A and B 0.0 at a, 1.0 at b location */
    public float tween = 0.5f;
    /** Offset between A and B. */
    public float ax,ay;
    public float bx,by;
    /** Maximum pixel distance from point A. */
    public float maxDistance = Float.MAX_VALUE;

    public Inbetween(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public Inbetween(int a, int b, float tween) {
        this.a = a;
        this.b = b;
        this.tween = tween;
    }

    public Inbetween() {
    }
}

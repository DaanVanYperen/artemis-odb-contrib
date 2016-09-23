package net.mostlyoriginal.api.component.physics;

import com.artemis.Component;
import com.artemis.annotations.EntityId;

/**
 * Accelerate entity towards target entity in a straight path.
 *
 * @author Daan van Yperen
 */
public class Homing extends Component {

    @EntityId
    public int target;

    public float speedFactor = 5f;

    // Distance within which this entity will accelerate.
    public float maxDistance = 999999f;

    public Homing(int target) {
        this.target = target;
    }
    public Homing() {
    }
}

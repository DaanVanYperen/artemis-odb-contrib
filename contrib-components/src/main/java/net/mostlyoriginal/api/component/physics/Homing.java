package net.mostlyoriginal.api.component.physics;

import com.artemis.Component;
import net.mostlyoriginal.api.Depends;
import net.mostlyoriginal.api.component.basic.Bounds;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.utils.reference.EntityReference;

/**
 * Accelerate entity towards target entity in a straight path.
 *
 * @author Daan van Yperen
 */
@Depends(value={Pos.class}, optional = {Bounds.class})
public class Homing extends Component {

    public EntityReference target;

    public float speedFactor = 5f;

    // Distance within which this entity will accelerate.
    public float maxDistance = 999999f;

    public Homing(EntityReference target) {
        this.target = target;
    }
}

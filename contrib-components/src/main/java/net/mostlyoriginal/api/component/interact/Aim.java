package net.mostlyoriginal.api.component.interact;

import com.artemis.Component;
import net.mostlyoriginal.api.utils.reference.EntityReference;

/**
 * Entity aims at target entity.
 *
 * @author Daan van Yperen
 */
public class Aim extends Component {
    public EntityReference at;

    public Aim(EntityReference at) {
        this.at = at;
    }

    public Aim() {
    }
}

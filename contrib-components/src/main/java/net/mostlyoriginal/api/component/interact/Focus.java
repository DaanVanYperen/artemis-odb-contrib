package net.mostlyoriginal.api.component.interact;

import com.artemis.Component;
import net.mostlyoriginal.api.utils.EntityReference;

/**
 * Focus of entity (for example, focus for aggression).
 *
 * @author Daan van Yperen
 */
public class Focus extends Component {
    public EntityReference entity;

    public Focus(EntityReference entity) {
        this.entity = entity;
    }
}

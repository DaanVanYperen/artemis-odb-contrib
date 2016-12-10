package net.mostlyoriginal.api.component.interact;

import com.artemis.Component;
import com.artemis.annotations.EntityId;

/**
 * Focus of entity (for example, focus for aggression).
 *
 * @author Daan van Yperen
 */
public class Focus extends Component {

    @EntityId
    public int entity;

    public Focus(int entity) {
        this.entity = entity;
    }

    public Focus() {
    }
}

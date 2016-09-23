package net.mostlyoriginal.api.component.interact;

import com.artemis.Component;
import com.artemis.annotations.EntityId;

/**
 * Entity aims at target entity.
 *
 * @author Daan van Yperen
 */
public class Aim extends Component {

    @EntityId
    public int at;

    public Aim(int at) {
        this.at = at;
    }

    public Aim() {
    }
}

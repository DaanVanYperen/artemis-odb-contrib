package net.mostlyoriginal.api.component.physics;

import com.artemis.Component;

import java.io.Serializable;

/**
 * Disable entity agency and physics.
 * Does not prevent interaction.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.system.physics.PhysicsSystem
 */
public class Frozen extends Component implements Serializable {
    public Frozen() {
    }
}

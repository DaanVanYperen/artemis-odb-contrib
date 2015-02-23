package net.mostlyoriginal.api.utils.reference;

import com.artemis.Entity;

/**
 * Abstract entity reference.
 *
 * @author Daan van Yperen
 */
public interface EntityReference {

	/** True if reference is available. */
    public boolean valid();

	/** Return entity from reference. */
    public Entity get();
}

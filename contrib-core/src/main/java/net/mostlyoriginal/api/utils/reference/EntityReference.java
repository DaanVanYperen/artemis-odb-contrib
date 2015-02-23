package net.mostlyoriginal.api.utils.reference;

import com.artemis.Entity;

/**
 * Abstract entity reference.
 *
 * @author Daan van Yperen
 */
public interface EntityReference {

	/** Return entity from reference, or <code>null</code> if none. */
    public Entity get();

}

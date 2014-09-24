package net.mostlyoriginal.api.utils.reference;

import com.artemis.Entity;

/**
 * @author Daan van Yperen
 */
public interface EntityReference {
    public boolean isActive();
    public Entity get();
}

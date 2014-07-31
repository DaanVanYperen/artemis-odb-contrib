package net.mostlyoriginal.api.utils;

import com.artemis.Entity;

/**
 * @author Daan van Yperen
 */
public interface EntityReference {
    public boolean isActive();
    public Entity get();
}

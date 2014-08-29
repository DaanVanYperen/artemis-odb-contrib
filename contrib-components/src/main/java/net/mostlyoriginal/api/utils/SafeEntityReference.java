package net.mostlyoriginal.api.utils;

import com.artemis.Entity;

import java.util.UUID;

/**
 * Reference by.. reference! :D
 *
 * @todo deserialize entity.
 * @author Daan van Yperen
 */
public class SafeEntityReference implements EntityReference {

    private UUID uuid;
    private transient Entity entity;

    public SafeEntityReference(Entity entity) {
        this.entity = entity;
        this.uuid = entity.getUuid();
    }

    public SafeEntityReference() {
    }

    @Override
    public boolean isActive() {
        final boolean active = entity != null && entity.getUuid().equals(uuid);
        if ( !active ) { entity = null; uuid = null; }
        return active;
    }

    @Override
    public Entity get() {
        return isActive() ? entity : null;
    }
}

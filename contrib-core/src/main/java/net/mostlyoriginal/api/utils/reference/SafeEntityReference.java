package net.mostlyoriginal.api.utils.reference;

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
    public boolean valid() {
        return entity != null && entity.getUuid().equals(uuid);
    }

    @Override
    public Entity get() {
        return valid() ? entity : null;
    }
}

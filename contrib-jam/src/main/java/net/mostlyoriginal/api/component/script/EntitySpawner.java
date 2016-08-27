package net.mostlyoriginal.api.component.script;

import com.artemis.Component;

/**
 * Spawn entity at random intervals.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.system.script.EntitySpawnerSystem
 */
public class EntitySpawner extends Component {
    public EntitySpawner() {
    }

    public String[] entityIds;

    /** min cooldown between spawns */
    public float minCooldown = 2;
    /** max cooldown between spawns */
    public float maxCooldown = 10;
    /** min entities spawned */
    public int   minCount    = 1;
    /** max entities spawned */
    public int   maxCount    = 5;

    /** Disable spawns */
    public boolean enabled = false;

    public float cooldown = Float.MIN_VALUE;

    /** Animation to apply to spawner while spawning. Null is not applied. */
    /** @todo abstract this into something neat. */
    public String animActiveId;
    /** Animation to apply to spawner while not spawning. Null is not applied. */
    /** @todo abstract this into something neat. */
    public String animInactiveId;

    public EntitySpawner(String entityId) {
        this.entityIds = new String[] {entityId};
    }

    public EntitySpawner(String[] entityIds) {
        this.entityIds = entityIds;
    }
}

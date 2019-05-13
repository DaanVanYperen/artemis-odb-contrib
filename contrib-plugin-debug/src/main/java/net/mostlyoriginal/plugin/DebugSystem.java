package net.mostlyoriginal.plugin;

import com.artemis.BaseSystem;
import com.artemis.EntityLifecycleListener;
import com.artemis.annotations.UnstableApi;

import java.util.HashMap;

/**
 * Logs entity lifecycle and can handle performance issues.
 * <p>
 * - Logs entity creation (CREATE).
 * - Log entity destruction (DELETE).
 * - Reports ERROR_ATTEMPT_TO_DELETE_DELETED_ENTITY when deleting an entity twice.
 * - Reports ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY when accessing an entity after deletion.
 * <p>
 * Adds a {@code DebugComponent} to all entities immediately at creation time.
 *
 * @author Daan van Yperen
 * @todo make DebugComponent adding to entity optional, to avoid issues caused by exotic user setups.
 * @see DebugPlugin
 */
@UnstableApi
public class DebugSystem extends BaseSystem implements EntityLifecycleListener {
    static final CharSequence CLASS_NAME = "net.mostlyoriginal.plugin.DebugSystem";

    final DebugLogStrategy logStrategy;
    final HashMap<Integer, DebugComponent> debugComponents = new HashMap<>();

    public DebugSystem() {
        logStrategy = new SystemOutDebugLogStrategy();
    }

    /**
     * @param logStrategy Event handler for debug incidents.
     */
    public DebugSystem(DebugLogStrategy logStrategy) {
        this.logStrategy = logStrategy;
    }

    @Override
    protected void processSystem() {
    }

    /**
     * Get debug component.
     * <p>
     * Useful when the world lifecycle isn't aware of the debug component yet.
     *
     * @param entityId
     * @return debug component if available or {@code null} if none.
     */
    public DebugComponent getDebugComponent(int entityId) {
        return debugComponents.containsKey(entityId) ? debugComponents.get(entityId) : null;
    }

    @Override
    public void onEntityDeleteIssued(int entityId) {
        DebugComponent debugComponent = debugComponents.get(entityId);
        if (debugComponent.isEntityDeleted()) {
            logStrategy.log(new DebugEventStacktrace(DebugEventStacktrace.Type.ERROR_ATTEMPT_TO_DELETE_DELETED_ENTITY, entityId, debugComponent.name,
                    Thread.currentThread().getStackTrace(), debugComponent.deletionStacktrace));

        }
        debugComponent.deletionStacktrace = new DebugEventStacktrace(DebugEventStacktrace.Type.DELETE, entityId, debugComponent.name, Thread.currentThread().getStackTrace());
        logStrategy.log(debugComponent.deletionStacktrace);
    }

    @Override
    public void onEntityCreated(int entityId) {

        // forget about any existing debug component entry.
        debugComponents.remove(entityId);

        // create component for user debug sake.
        DebugComponent debugComponent = world.getEntity(entityId).edit().create(DebugComponent.class);

        debugComponent.creationStacktrace = new DebugEventStacktrace(DebugEventStacktrace.Type.CREATE, entityId, debugComponent.name, Thread.currentThread().getStackTrace());

        // the world lifecycle is delayed such that we need our own way to resolve debug components in case of create/destroy within the same system;
        debugComponents.put(entityId, debugComponent);

        logStrategy.log(debugComponent.creationStacktrace);
    }

    @Override
    public void onEntityNotFoundException(int entityId) {
        DebugComponent debugComponent = debugComponents.get(entityId);
        if (debugComponent != null && debugComponent.isEntityDeleted()) {
            logStrategy.log(new DebugEventStacktrace(DebugEventStacktrace.Type.ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY, entityId,
                    debugComponent.name, Thread.currentThread().getStackTrace(), debugComponent.deletionStacktrace));
        }
    }

}

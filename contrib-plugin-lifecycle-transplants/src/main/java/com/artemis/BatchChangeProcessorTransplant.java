package com.artemis;

import net.mostlyoriginal.plugin.LifecycleListener.Type;
import net.onedaybeard.graftt.Graft;

/**
 * BatchChangeProcessor with callbacks for relevant listener events.
 *
 * @author Daan van Yperen
 */
@SuppressWarnings("InfiniteRecursion")
@Graft.Recipient(BatchChangeProcessor.class)
class BatchChangeProcessorTransplant {
    @Graft.Mock
    private WorldTransplant world;

    @Graft.Fuse
    void delete(int entityId) {
        world.lifecycleListener.onLifecycleEvent(Type.ENTITY_DELETE_PLANNED, entityId, null);
        delete(entityId);
    }

    @Graft.Fuse
    EntityEdit obtainEditor(int entityId) {
        world.lifecycleListener.onLifecycleEvent(Type.ENTITY_EDIT_PRE, entityId, null);
        return obtainEditor(entityId);
    }
}

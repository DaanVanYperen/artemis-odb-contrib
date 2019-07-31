package com.artemis;

import net.mostlyoriginal.plugin.LifecycleListener.Type;
import net.onedaybeard.graftt.Graft;

/**
 *  LifecycleListenerEntityManager with callbacks for relevant lifecycle events.
 *
 *  @author Daan van Yperen
 */
@SuppressWarnings("InfiniteRecursion")
@Graft.Recipient(EntityManager.class)
class LifecycleListenerEntityManager {
    @Graft.Mock
    private WorldTransplant world;

    @Graft.Fuse
    protected Entity createEntityInstance() {
        final Entity entityInstance = createEntityInstance();
        lifecycleEvent(Type.ENTITY_CREATE_POST, entityInstance.id);
        return entityInstance;
    }

    @Graft.Fuse
    protected Entity getEntity(int entityId) {
        lifecycleEvent(Type.ENTITY_GET_PRE, entityId);
        return getEntity(entityId);
    }

    @Graft.Fuse
    public boolean isActive(int entityId) {
        lifecycleEvent(Type.ENTITY_ISACTIVE_CHECK_PRE, entityId);
        return isActive(entityId);
    }

    @Graft.Fuse
    protected int create() {
        final int id = create();
        lifecycleEvent(Type.ENTITY_CREATE_POST, id);
        return id;
    }

    private void lifecycleEvent(Type type, int entityId) {
        world.lifecycleListener.onLifecycleEvent(type, entityId, null);
    }
}

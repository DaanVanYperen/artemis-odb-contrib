package com.artemis;

import net.mostlyoriginal.plugin.LifecycleListener;

/**
 *  LifecycleListenerEntityManager with callbacks for relevant lifecycle events.
 *
 *  @author Daan van Yperen
 */
class LifecycleListenerEntityManager extends EntityManager {
    LifecycleListener listener;

    @Override
    protected Entity createEntityInstance() {
        final Entity entityInstance = super.createEntityInstance();
        listener.onLifecycleEvent(LifecycleListener.Type.ENTITY_CREATE_POST, entityInstance.id, null);
        return entityInstance;
    }

    @Override
    protected Entity getEntity(int entityId) {
        listener.onLifecycleEvent(LifecycleListener.Type.ENTITY_GET_PRE, entityId, null);
        return super.getEntity(entityId);
    }

    @Override
    public boolean isActive(int entityId) {
        listener.onLifecycleEvent(LifecycleListener.Type.ENTITY_ISACTIVE_CHECK_PRE, entityId, null);
        return super.isActive(entityId);
    }

    @Override
    protected int create() {
        final int id = super.create();
        listener.onLifecycleEvent(LifecycleListener.Type.ENTITY_CREATE_POST, id, null);
        return id;
    }


    protected LifecycleListenerEntityManager(int entityContainerSize, LifecycleListener listener) {
        super(entityContainerSize);
        this.listener = listener;
    }
}

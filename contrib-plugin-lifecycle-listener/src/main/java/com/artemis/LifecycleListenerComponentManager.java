package com.artemis;

import com.artemis.utils.Bag;
import com.artemis.utils.IntBag;
import net.mostlyoriginal.plugin.LifecycleListener;

/**
 * LifecycleListenerComponentManager with callbacks for relevant lifecycle events.
 *
 * @author Daan van Yperen
 */
class LifecycleListenerComponentManager extends ComponentManager {
    LifecycleListener listener;

    @Override
    public int getIdentity(int entityId) {
        listener.onLifecycleEvent(LifecycleListener.Type.ENTITY_IDENTITY_PRE, entityId, null);
        return super.getIdentity(entityId);
    }

    @Override
    public Bag<Component> getComponentsFor(int entityId, Bag<Component> fillBag) {
        listener.onLifecycleEvent(LifecycleListener.Type.ENTITY_COMPONENTS_PRE, entityId, null);
        return super.getComponentsFor(entityId, fillBag);
    }

    @Override
    void clean(IntBag pendingPurge) {
        listener.onLifecycleEvent(LifecycleListener.Type.COMPONENTMANAGER_CLEAN_PRE, -1, null);
        super.clean(pendingPurge);
        listener.onLifecycleEvent(LifecycleListener.Type.COMPONENTMANAGER_CLEAN_POST, -1, null);
    }

    @Override
    protected <T extends Component> ComponentMapper<T> instanceOfComponentMapper(Class<T> type) {
        return new LifecycleListenerComponentMapper<>(type, world, listener);
    }

    protected LifecycleListenerComponentManager(int entityContainerSize, LifecycleListener listener) {
        super(entityContainerSize);
        this.listener = listener;
    }

}

package com.artemis;

import net.mostlyoriginal.plugin.LifecycleListener;
import net.mostlyoriginal.plugin.LifecycleListener.Type;
import net.onedaybeard.graftt.Graft;

/**
 * LifecycleListenerComponentMapper with callbacks for relevant lifecycle events.
 *
 * @author Daan van Yperen
 */
@SuppressWarnings("InfiniteRecursion")
@Graft.Recipient(ComponentMapper.class)
class LifecycleListenerComponentMapper<A extends Component> {

    @SuppressWarnings("WeakerAccess")
    public LifecycleListener listener;

    @Graft.Mock
    public ComponentType type;

    @Graft.Fuse
    public A get(int entityId) throws ArrayIndexOutOfBoundsException {
        listener.onLifecycleEvent(Type.COMPONENT_GET_PRE, entityId, type);
        return get(entityId);
    }

    @Graft.Fuse
    public boolean has(int entityId) {
        listener.onLifecycleEvent(Type.COMPONENT_HAS_PRE, entityId, type);
        return has(entityId);
    }

    @Graft.Fuse
    public void remove(int entityId) {
        listener.onLifecycleEvent(Type.COMPONENT_REMOVE_PRE, entityId, type);
        remove(entityId);
    }

    @Graft.Fuse
    protected void internalRemove(int entityId) {
        listener.onLifecycleEvent(Type.COMPONENT_INTERNAL_REMOVE_PRE, entityId, type);
        internalRemove(entityId);
    }

    @Graft.Fuse
    public A create(int entityId) {
        listener.onLifecycleEvent(Type.COMPONENT_CREATE_PRE, entityId, type);
        return create(entityId);
    }

    @Graft.Fuse
    public A internalCreate(int entityId) {
        listener.onLifecycleEvent(Type.COMPONENT_INTERNAL_CREATE_PRE, entityId, type);
        return internalCreate(entityId);
    }
}

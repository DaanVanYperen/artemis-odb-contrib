package com.artemis;

import net.mostlyoriginal.plugin.LifecycleListener;

/**
 * LifecycleListenerComponentMapper with callbacks for relevant lifecycle events.
 *
 * @author Daan van Yperen
 */
class LifecycleListenerComponentMapper<A extends Component> extends ComponentMapper<A> {
    LifecycleListener listener;

    public LifecycleListenerComponentMapper(Class<A> type, World world, LifecycleListener listener) {
        super(type, world);
        this.listener = listener;
    }


    @Override
    public A get(int entityId) throws ArrayIndexOutOfBoundsException {
        listener.onLifecycleEvent(LifecycleListener.Type.COMPONENT_GET_PRE, entityId, type);
        A result = super.get(entityId);
        return result;
    }

    @Override
    public boolean has(int entityId) {
        listener.onLifecycleEvent(LifecycleListener.Type.COMPONENT_HAS_PRE, entityId, type);
        boolean result = super.has(entityId);
        return result;
    }

    @Override
    public void remove(int entityId) {
        listener.onLifecycleEvent(LifecycleListener.Type.COMPONENT_REMOVE_PRE, entityId, type);
        super.remove(entityId);
    }

    @Override
    protected void internalRemove(int entityId) {
        listener.onLifecycleEvent(LifecycleListener.Type.COMPONENT_INTERNAL_REMOVE_PRE, entityId, type);
        super.internalRemove(entityId);
    }

    @Override
    public A create(int entityId) {
        listener.onLifecycleEvent(LifecycleListener.Type.COMPONENT_CREATE_PRE, entityId, type);
        A result = super.create(entityId);
        return result;
    }

    @Override
    public A internalCreate(int entityId) {
        listener.onLifecycleEvent(LifecycleListener.Type.COMPONENT_INTERNAL_CREATE_PRE, entityId, type);
        return super.internalCreate(entityId);
    }
}

package com.artemis;

import com.artemis.utils.Bag;
import com.artemis.utils.IntBag;
import net.mostlyoriginal.plugin.LifecycleListener.Type;
import net.onedaybeard.graftt.Graft;

/**
 * LifecycleListenerComponentManager with callbacks for relevant lifecycle events.
 *
 * @author Daan van Yperen
 */
@SuppressWarnings("InfiniteRecursion")
@Graft.Recipient(ComponentManager.class)
class LifecycleListenerComponentManager  {
    @Graft.Mock
    private WorldTransplant world;

    @Graft.Mock
    private Bag<LifecycleListenerComponentMapper> mappers;

    // see note in this::registerComponentType
    protected void initialize() {
        for (LifecycleListenerComponentMapper cm : mappers) {
            cm.listener = world.lifecycleListener;
        }
    }

    @Graft.Fuse
    public int getIdentity(int entityId) {
        lifecycleEvent(Type.ENTITY_IDENTITY_PRE, entityId);
        return getIdentity(entityId);
    }

    @Graft.Fuse
    public Bag<Component> getComponentsFor(int entityId, Bag<Component> fillBag) {
        lifecycleEvent(Type.ENTITY_COMPONENTS_PRE, entityId);
        return getComponentsFor(entityId, fillBag);
    }

    @Graft.Fuse
    void clean(IntBag pendingPurge) {
        for (int i = 0, s = pendingPurge.size(); i < s; i++) {
            // must run /after/ COMPONENTMANAGER_CLEAN_PRE
            lifecycleEvent(Type.ENTITY_DELETE_FINALIZED, pendingPurge.get(i));
        }
        lifecycleEvent(Type.COMPONENTMANAGER_CLEAN_PRE, -1);
        clean(pendingPurge);
        lifecycleEvent(Type.COMPONENTMANAGER_CLEAN_POST, -1);
    }

    @Graft.Fuse
    void registerComponentType(ComponentType ct, int capacity) {
        registerComponentType(ct, capacity);

        // there's no guarantee that world has been initialized
        // at this point; hence initialize() also re-registers
        // the listeners as world.lifecycleListener was null
        // prior to that point
        mappers.get(ct.getIndex()).listener = world.lifecycleListener;
    }

    private void lifecycleEvent(Type type, int entityId) {
        world.lifecycleListener.onLifecycleEvent(type, entityId, null);
    }
}

package com.artemis;

import net.mostlyoriginal.plugin.LifecycleListener;

/**
 * BatchChangeProcessor with callbacks for relevant listener events.
 *
 * @author Daan van Yperen
 */
class LifecycleListenerBatchChangeProcessor extends BatchChangeProcessor {
    LifecycleListener listener;

    @Override
    void delete(int entityId) {
        listener.onLifecycleEvent(LifecycleListener.Type.ENTITY_DELETE_PLANNED, entityId, null);
        super.delete(entityId);
    }

    @Override
    EntityEdit obtainEditor(int entityId) {
        listener.onLifecycleEvent(LifecycleListener.Type.ENTITY_EDIT_PRE, entityId, null);
        return super.obtainEditor(entityId);
    }

    protected LifecycleListenerBatchChangeProcessor(World w, LifecycleListener listener) {
        super(w);
        this.listener = listener;
    }
}

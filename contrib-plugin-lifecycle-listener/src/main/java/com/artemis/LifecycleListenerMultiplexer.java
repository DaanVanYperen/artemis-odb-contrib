package com.artemis;

import net.mostlyoriginal.plugin.LifecycleListener;

/**
 * Multiplexer for lifecycle events, if there is more than one interested party.
 *
 * @author Daan van Yperen
 */
class LifecycleListenerMultiplexer implements LifecycleListener {
    private final LifecycleListener[] listeners;

    public LifecycleListenerMultiplexer(LifecycleListener[] listeners) {
        this.listeners = listeners;
    }

    @Override
    public void onLifecycleEvent(Type event, int entityId, Object optionalArg) {
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].onLifecycleEvent(event, entityId, optionalArg);
        }
    }
}

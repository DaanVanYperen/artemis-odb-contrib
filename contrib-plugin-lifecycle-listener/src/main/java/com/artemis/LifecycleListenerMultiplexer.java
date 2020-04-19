package com.artemis;

import com.artemis.utils.Bag;
import net.mostlyoriginal.plugin.LifecycleListener;

/**
 * Multiplexer for lifecycle events, if there is more than one interested party.
 *
 * @author Daan van Yperen
 */
class LifecycleListenerMultiplexer implements LifecycleListener {
    private final Bag<LifecycleListener> listeners = new Bag<>(LifecycleListener.class);

    public LifecycleListenerMultiplexer(LifecycleListener[] listeners) {
        for (int i = 0; i < listeners.length; i++) {
            addListener(listeners[i]);
        }
    }

    public LifecycleListenerMultiplexer() {}

    public void addListener(LifecycleListener listener) {
        listeners.add(listener);
    }

    @Override
    public void onLifecycleEvent(Type event, int entityId, Object optionalArg) {
        for (int i = 0, s = listeners.size(); i < s; i++) {
            listeners.get(i).onLifecycleEvent(event, entityId, optionalArg);
        }
    }
}

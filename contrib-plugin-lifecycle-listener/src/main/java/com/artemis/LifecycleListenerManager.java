package com.artemis;

import com.artemis.utils.ImmutableBag;
import net.mostlyoriginal.plugin.ArtemisPhaseListener;
import net.mostlyoriginal.plugin.LifecycleListener;

/** LifecycleListener that will inject the required hooks and ensures systems that implement LifecycleListener get called. */
public class LifecycleListenerManager extends BaseSystem implements ArtemisPhaseListener {
    private LifecycleListenerMultiplexer listeners = new LifecycleListenerMultiplexer();

    @Override
    public void onPhase(World w, Phase phase) {
        if (phase == Phase.PRE_INITIALIZE) {
            // world on system isn't available at this point in the lifecycle.
            registerEntityLifecycleListeners(w.getSystems());
        }
    }

    // find all systems that implement lifecycle listeners.
    private void registerEntityLifecycleListeners(ImmutableBag<BaseSystem> systems) {
        for (BaseSystem system : systems) {
            if (system instanceof LifecycleListener) {
                addListener((LifecycleListener) system);
            }
        }
    }

    public void addListener(LifecycleListener listener) {
        listeners.addListener(listener);
    }

    public LifecycleListener lifecycleListener() {
        return listeners;
    }

    @Override
    protected void processSystem() {
    }
}

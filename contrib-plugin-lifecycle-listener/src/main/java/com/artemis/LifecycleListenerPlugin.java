package com.artemis;

import com.artemis.annotations.UnstableApi;
import com.artemis.utils.ImmutableBag;
import com.artemis.utils.InterfaceUtil;
import net.mostlyoriginal.plugin.LifecycleListener;

/**
 * Plugin that hooks into odb's lifecycle.
 *
 * This plugin has an performance impact. Avoid using for anything but internal tooling (debuggers/editors).
 *
 * Instructions:
 * 1. install this plugin in worldconfiguration(builder).
 * 2. implement {@code net.mostlyoriginal.plugin.LifecycleListener} on a system.
 *
 * Does not support worlds with customized ComponentMapper, EntityManager, SubscriptionManager, BatchChangeProcessor.
 *
 * @author Daan van Yperen
 */
@UnstableApi
public class LifecycleListenerPlugin implements ArtemisPlugin {

    private LifecycleListener lifecycleListener;

    @Override
    public void setup(WorldConfigurationBuilder b) {
        b.with(WorldConfigurationBuilder.Priority.LOWEST, new LifecycleListenerManager());
        b.register(new LifecycleListenerExtensionInternalFactory());
    }

    /** LifecycleListener that will inject the required hooks and ensures systems that implement LifecycleListener get called. */
    private class LifecycleListenerManager extends BaseSystem implements ArtemisPhaseListener {

        @Override
        public void onPhase(Phase phase) {
            if (phase == Phase.PRE_INITIALIZE) {
                registerEntityLifecycleListeners(world.getSystems());
            }
        }

        // find all systems that implement lifecycle listeners.
        private void registerEntityLifecycleListeners(ImmutableBag<BaseSystem> systems) {
            final LifecycleListener[] listeners = InterfaceUtil.getObjectsImplementing(systems, LifecycleListener.class, new LifecycleListener[0]);
            if (listeners != null) {
                // wrap listeners in multiplexer if we have more than one. otherwise just call it directly.s
                lifecycleListener = listeners.length == 1 ? listeners[0] : new LifecycleListenerMultiplexer(listeners);
            }
        }

        @Override
        protected void processSystem() {
        }
    }

    /** Factory that injects internal classes with lifecycle callbacks. */
    private class LifecycleListenerExtensionInternalFactory extends InternalFactory {

        @Override
        ComponentManager createComponentManager(int expectedEntityCount) {
            return new LifecycleListenerComponentManager(expectedEntityCount, lifecycleListener);
        }

        @Override
        EntityManager createEntityManager(int expectedEntityCount) {
            return new LifecycleListenerEntityManager(expectedEntityCount, lifecycleListener);
        }

        @Override
        AspectSubscriptionManager createSubscriptionManager() {
            return new AspectSubscriptionManager();
        }

        @Override
        BatchChangeProcessor createBatchChangeProcessor(World w) {
            return new LifecycleListenerBatchChangeProcessor(w, lifecycleListener);
        }
    }
}

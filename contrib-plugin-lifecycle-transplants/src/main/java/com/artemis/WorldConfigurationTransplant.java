package com.artemis;

import com.artemis.injection.Injector;
import com.artemis.utils.Bag;
import net.mostlyoriginal.plugin.LifecycleListener;
import net.onedaybeard.graftt.Graft;

@SuppressWarnings("InfiniteRecursion")
@Graft.Recipient(WorldConfiguration.class)
class WorldConfigurationTransplant {

    @Graft.Mock
    Bag<BaseSystem> systems;

    @Graft.Fuse
    void initialize(WorldTransplant world, // transplant recipient substitution ftw!
                    Injector injector,
                    AspectSubscriptionManager asm) {

        LifecycleListenerManager llm = system(LifecycleListenerManager.class);
        world.lifecycleListener = llm.lifecycleListener();

        for (BaseSystem system : systems) {
            if (system instanceof LifecycleListener) {
                llm.addListener((LifecycleListener) system);
            }
        }

        initialize(world, injector, asm);
    }

    private <T extends BaseSystem> T system(Class<T> s) {
        for (BaseSystem system : systems) {
            if (s.isInstance(system))
                return (T) system;
        }

        throw new RuntimeException(
            "LifecycleListenerManager not registered with world");
    }
}

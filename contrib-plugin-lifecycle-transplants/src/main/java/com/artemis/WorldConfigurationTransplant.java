package com.artemis;

import com.artemis.injection.Injector;
import com.artemis.utils.Bag;
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

        for (BaseSystem system : systems) {
            if (!(system instanceof LifecycleListenerManager))
                continue;

            LifecycleListenerManager llm = (LifecycleListenerManager) system;
            world.lifecycleListener = llm.lifecycleListener();
            break;
        }

        initialize(world, injector, asm);
    }
}

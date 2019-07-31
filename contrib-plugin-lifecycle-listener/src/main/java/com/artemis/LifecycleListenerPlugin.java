package com.artemis;

import com.artemis.annotations.UnstableApi;

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

    @Override
    public void setup(WorldConfigurationBuilder b) {
        b.with(WorldConfigurationBuilder.Priority.LOWEST, new LifecycleListenerManager());
    }
}

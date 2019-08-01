package com.artemis;

import net.mostlyoriginal.plugin.LifecycleListener;
import net.onedaybeard.graftt.Graft;

@Graft.Recipient(World.class)
class WorldTransplant {
    // set during WorldConfiguration::initialize
    public LifecycleListener lifecycleListener;
}

package net.mostlyoriginal.plugin;

import net.onedaybeard.graftt.Graft;

@Graft.Recipient(DebugPlugin.class)
public class DebugPluginTransplant {

    @Graft.Fuse
    public static boolean isArtemisTransformedForDebugging() {
        return true;
    }
}

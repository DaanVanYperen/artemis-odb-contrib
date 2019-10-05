package net.mostlyoriginal.plugin;

import com.artemis.World;

public interface ArtemisPhaseListener {

     enum Phase {
        PRE_INITIALIZE,
        POST_INITIALIZE,
        PRE_DISPOSE,
        POST_DISPOSE;
    }

     void onPhase(World w, Phase phase);
}
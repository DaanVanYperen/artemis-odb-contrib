package net.mostlyoriginal.api.system.core;

import com.artemis.BaseSystem;import java.lang.Override;

/**
 * Headless system.
 *
 * For tickless secondary systems that service primary systems.
 *
 * @author Daan van Yperen
 */
public class PassiveSystem extends BaseSystem {

    public PassiveSystem() {
        setPassive(true);
    }

    @Override
    protected void processSystem() {
	    // do nothing!
    }
}

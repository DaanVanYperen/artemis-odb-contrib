package net.mostlyoriginal.api.system.core;

import com.artemis.BaseSystem;

/**
 * Headless system.
 *
 * For tickless secondary systems that service primary systems.
 *
 * @author Daan van Yperen
 */
public class PassiveSystem extends BaseSystem {

    public PassiveSystem() {
        
    }

    @Override
    protected void processSystem() {
	    // do nothing!
    }
    
    @Override
    protected boolean checkProcessing() {
    	setEnabled(false);
    	return false;
    }
}

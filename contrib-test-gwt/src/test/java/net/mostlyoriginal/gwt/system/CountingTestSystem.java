package net.mostlyoriginal.gwt.system;

import com.artemis.systems.VoidEntitySystem;

/**
 * @author Daan van Yperen
 */
public class CountingTestSystem extends VoidEntitySystem {
    public int count = 0;

    @Override
    protected void processSystem() {
        count++;
    }
}

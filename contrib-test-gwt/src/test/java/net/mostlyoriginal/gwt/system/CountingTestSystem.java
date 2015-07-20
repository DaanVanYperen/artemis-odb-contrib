package net.mostlyoriginal.gwt.system;

import com.artemis.BaseSystem;

/**
 * @author Daan van Yperen
 */
public class CountingTestSystem extends BaseSystem {
    public int count = 0;

    @Override
    protected void processSystem() {
        count++;
    }
}

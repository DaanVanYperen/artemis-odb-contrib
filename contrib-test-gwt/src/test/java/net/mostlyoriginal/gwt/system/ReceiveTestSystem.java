package net.mostlyoriginal.gwt.system;

import com.artemis.systems.VoidEntitySystem;
import net.mostlyoriginal.api.event.common.Subscribe;

/**
* @author Daan van Yperen
*/
public class ReceiveTestSystem extends VoidEntitySystem {
    public int count = 0;

    @Override
    protected void processSystem() {
    }

    @Subscribe
    public void testEvent(BasicTestEvent event) {
        count++;
    }
}

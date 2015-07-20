package net.mostlyoriginal.gwt.system;

import com.artemis.BaseSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

/**
* @author Daan van Yperen
*/
public class ReceiveTestSystem extends BaseSystem {
    public int count = 0;

    @Override
    protected void processSystem() {
    }

    @Subscribe
    public void testEvent(BasicTestEvent event) {
        count++;
    }
}

package net.mostlyoriginal.gwt.system;

import com.artemis.annotations.Wire;
import com.artemis.systems.VoidEntitySystem;
import net.mostlyoriginal.api.event.common.EventManager;

/**
* @author Daan van Yperen
*/
@Wire
public class DispatchTestSystem extends VoidEntitySystem {

    EventManager eventManager;

    @Override
    protected void processSystem() {
        eventManager.dispatch(new BasicTestEvent());
    }
}

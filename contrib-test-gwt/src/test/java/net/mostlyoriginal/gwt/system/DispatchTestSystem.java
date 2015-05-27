package net.mostlyoriginal.gwt.system;

import com.artemis.annotations.Wire;
import com.artemis.systems.VoidEntitySystem;
import net.mostlyoriginal.api.event.common.EventSystem;

/**
* @author Daan van Yperen
*/
@Wire
public class DispatchTestSystem extends VoidEntitySystem {

    EventSystem eventSystem;

    @Override
    protected void processSystem() {
        eventSystem.dispatch(new BasicTestEvent());
    }
}

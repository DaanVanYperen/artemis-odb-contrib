package net.mostlyoriginal.gwt.system;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import net.mostlyoriginal.api.event.common.EventSystem;

/**
* @author Daan van Yperen
*/
@Wire
public class DispatchTestSystem extends BaseSystem {

    EventSystem eventSystem;

    @Override
    protected void processSystem() {
        eventSystem.dispatch(new BasicTestEvent());
    }
}

package net.mostlyoriginal.api.operation.act;

import com.artemis.Component;
import com.artemis.Entity;
import net.mostlyoriginal.api.component.Schedule;
import net.mostlyoriginal.api.operation.common.Operation;

/**
 * Script Remove component from entity by class.
 *
 * @author Daan van Yperen
 * @see Schedule
 */
public class RemoveOperation extends Operation {

    public Class<? extends Component> componentClass;

    public RemoveOperation() {
    }

    @Override
    public boolean process(float delta, Entity e) {
        e.edit().remove(componentClass);
        return true;
    }

    @Override
    public void reset() {
        componentClass = null;
    }
}

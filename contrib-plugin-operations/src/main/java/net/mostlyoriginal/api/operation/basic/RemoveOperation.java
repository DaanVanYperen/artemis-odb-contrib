package net.mostlyoriginal.api.operation.basic;

import com.artemis.Component;
import com.artemis.Entity;
import net.mostlyoriginal.api.component.Schedule;
import net.mostlyoriginal.api.operation.common.BasicOperation;

/**
 * Remove component from entity.
 *
 * @author Daan van Yperen
 * @see Schedule
 */
public class RemoveOperation extends BasicOperation {

    public Class<? extends Component> componentClass;

    public RemoveOperation() {
    }

    @Override
    public void process(Entity e) {
        e.edit().remove(componentClass);
    }

    @Override
    public void reset() {
        super.reset();
        componentClass = null;
    }
}

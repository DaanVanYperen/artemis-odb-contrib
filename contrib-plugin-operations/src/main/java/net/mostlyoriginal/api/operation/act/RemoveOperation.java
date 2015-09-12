package net.mostlyoriginal.api.operation.act;

import com.artemis.Component;
import com.artemis.Entity;
import net.mostlyoriginal.api.component.Schedule;

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
        componentClass = null;
    }
}

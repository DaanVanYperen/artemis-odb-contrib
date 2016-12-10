package net.mostlyoriginal.api.operation.basic;

import com.artemis.Component;
import com.artemis.Entity;
import net.mostlyoriginal.api.component.Schedule;
import net.mostlyoriginal.api.operation.common.BasicOperation;

/**
 * Create component in entity.
 *
 * @author Daan van Yperen
 * @see Schedule
 */
public class CreateOperation extends BasicOperation {

    public Class<? extends Component> componentClass;

    public CreateOperation() {
    }

    @Override
    public void process(Entity e) {
        e.edit().create(componentClass);
    }

    @Override
    public void reset() {
        super.reset();
        componentClass = null;
    }
}

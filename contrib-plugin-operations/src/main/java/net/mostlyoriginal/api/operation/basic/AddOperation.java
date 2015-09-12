package net.mostlyoriginal.api.operation.basic;

import com.artemis.Component;
import com.artemis.Entity;
import net.mostlyoriginal.api.component.Schedule;
import net.mostlyoriginal.api.operation.common.BasicOperation;

/**
 * Add component to aen
 *
 * @author Daan van Yperen
 * @see Schedule
 */
public class AddOperation extends BasicOperation {

    public Component component;
    public AddOperation() {}

    @Override
    public void process(Entity e) {
        e.edit().add(component);
    }

    @Override
    public void reset() {
        super.reset();
        component = null;
    }
}

package net.mostlyoriginal.api.operation.act;

import com.artemis.Component;
import com.artemis.Entity;
import net.mostlyoriginal.api.component.Schedule;

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
        component = null;
    }
}

package net.mostlyoriginal.api.operation.act;

import com.artemis.Component;
import com.artemis.Entity;
import net.mostlyoriginal.api.component.Schedule;
import net.mostlyoriginal.api.operation.common.Operation;

/**
 * Add component to entity.
 *
 * @author Daan van Yperen
 * @see Schedule
 */
public class AddOperation extends Operation {

    public Component component;
    public AddOperation() {}

    @Override
    public boolean process(float delta, Entity e) {
        e.edit().add(component);
        return true;
    }

    @Override
    public void reset() {
        component = null;
    }
}

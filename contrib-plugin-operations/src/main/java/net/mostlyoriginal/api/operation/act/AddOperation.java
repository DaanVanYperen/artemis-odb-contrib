package net.mostlyoriginal.api.operation.act;

import com.artemis.Component;
import com.artemis.Entity;
import net.mostlyoriginal.api.component.Schedule;
import net.mostlyoriginal.api.operation.common.Operation;

/**
 * Script Add component to actor.
 *
 * @author Daan van Yperen
 * @see Schedule
 */
public class AddOperation extends Operation {

    public Component component;
    public AddOperation() {}

    @Override
    public boolean act(float delta, Entity e) {
        e.edit().add(component);
        return true;
    }

    @Override
    public void reset() {
        component = null;
    }
}

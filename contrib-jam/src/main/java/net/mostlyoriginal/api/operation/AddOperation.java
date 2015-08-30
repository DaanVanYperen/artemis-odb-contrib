package net.mostlyoriginal.api.operation;

import com.artemis.Component;
import com.artemis.Entity;

/**
 * Script Add component to actor.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.script.Schedule
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

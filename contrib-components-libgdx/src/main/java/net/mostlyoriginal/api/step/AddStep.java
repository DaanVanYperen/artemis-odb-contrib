package net.mostlyoriginal.api.step;

import com.artemis.Component;
import com.artemis.Entity;

/**
 * Script Add component to actor.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.script.Schedule
 */
public class AddStep extends Step {

    public Component component;
    public AddStep() {}

    @Override
    public boolean act(float delta, Entity e) {
        e.addComponent(component);
        return true;
    }

    @Override
    public void reset() {
        component = null;
    }
}

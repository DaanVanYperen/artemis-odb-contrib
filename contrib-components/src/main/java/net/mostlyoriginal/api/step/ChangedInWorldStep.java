package net.mostlyoriginal.api.step;

import com.artemis.Entity;

/**
 * Script changed in world step.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.script.Schedule
 */
public class ChangedInWorldStep extends Step {

    public ChangedInWorldStep() {}

    @Override
    public boolean act(float delta, Entity e) {
        e.changedInWorld();
        return true;
    }

    @Override
    public void reset() {
    }
}

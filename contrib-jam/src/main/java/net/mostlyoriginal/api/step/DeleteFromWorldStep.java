package net.mostlyoriginal.api.step;

import com.artemis.Entity;

/**
 * Script Delete Entity from world.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.script.Schedule
 */
public class DeleteFromWorldStep extends Step {

    public DeleteFromWorldStep() {}

    @Override
    public boolean act(float delta, Entity e) {
        e.deleteFromWorld();
        return true;
    }

    @Override
    public void reset() {
    }
}

package net.mostlyoriginal.api.operation;

import com.artemis.Entity;

/**
 * Script Delete Entity from world.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.script.Schedule
 */
public class DeleteFromWorldOperation extends Operation {

    public DeleteFromWorldOperation() {}

    @Override
    public boolean act(float delta, Entity e) {
        e.deleteFromWorld();
        return true;
    }

    @Override
    public void reset() {
    }
}

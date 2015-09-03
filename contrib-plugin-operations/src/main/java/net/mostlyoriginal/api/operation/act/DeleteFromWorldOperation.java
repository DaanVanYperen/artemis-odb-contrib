package net.mostlyoriginal.api.operation.act;

import com.artemis.Entity;
import net.mostlyoriginal.api.component.Schedule;
import net.mostlyoriginal.api.operation.common.Operation;

/**
 * Script Delete Entity from world.
 *
 * @author Daan van Yperen
 * @see Schedule
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

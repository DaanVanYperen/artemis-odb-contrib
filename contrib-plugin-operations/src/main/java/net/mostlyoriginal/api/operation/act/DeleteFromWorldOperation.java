package net.mostlyoriginal.api.operation.act;

import com.artemis.Entity;
import net.mostlyoriginal.api.component.Schedule;

/**
 * Delete Entity from world.
 *
 * @author Daan van Yperen
 * @see Schedule
 */
public class DeleteFromWorldOperation extends BasicOperation {

    public DeleteFromWorldOperation() {}

    @Override
    public void process(Entity e) {
        e.deleteFromWorld();
    }

    @Override
    public void reset() {
    }
}

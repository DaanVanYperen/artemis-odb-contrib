package net.mostlyoriginal.api.operation.basic;

import com.artemis.Entity;
import net.mostlyoriginal.api.component.Schedule;
import net.mostlyoriginal.api.operation.common.BasicOperation;

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
}

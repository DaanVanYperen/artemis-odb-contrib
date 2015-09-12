package net.mostlyoriginal.api.operation.temporal;

import com.artemis.Entity;
import net.mostlyoriginal.api.component.Schedule;
import net.mostlyoriginal.api.operation.common.TemporalOperation;

/**
 * Delay.
 *
 * @author Daan van Yperen
 * @see Schedule
 */
public class DelayOperation extends TemporalOperation {

    public DelayOperation() {}

    @Override
    public void act(float percentage, Entity e) {
        // do nothing.
    }
}

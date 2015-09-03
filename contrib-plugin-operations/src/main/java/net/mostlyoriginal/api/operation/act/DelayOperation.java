package net.mostlyoriginal.api.operation.act;

import com.artemis.Entity;
import net.mostlyoriginal.api.component.Schedule;
import net.mostlyoriginal.api.operation.common.Operation;

/**
 * Delay.
 *
 * @author Daan van Yperen
 * @see Schedule
 */
public class DelayOperation extends Operation {

    private float age;
    private float delay;

    public DelayOperation() {}

    public void setDelay(float delay) {
        this.delay = delay;
    }

    @Override
    public boolean process(float delta, Entity e) {
        age += delta;
        return age >= delay;
    }

    @Override
    public void reset() {
        age=0;
        delay=0;
    }
}

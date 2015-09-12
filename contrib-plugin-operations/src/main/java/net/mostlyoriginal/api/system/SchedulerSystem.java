package net.mostlyoriginal.api.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import net.mostlyoriginal.api.component.Schedule;
import net.mostlyoriginal.api.operation.flow.ParallelOperation;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;

/**
 * Perform scheduled actions.
 *
 * @author Daan van Yperen
 * @see Schedule
 */
@Wire
public class SchedulerSystem extends EntityProcessingSystem {

    protected M<Schedule> mSchedule;

    public SchedulerSystem() {
        super(Aspect.all(Schedule.class));
    }

    @Override
    protected void process(Entity e) {
        ParallelOperation operation = mSchedule.get(e).operation;
        if ( operation.process(world.delta, e) ) {
            // Done. return schedule to pool.
            mSchedule.remove(e);
        }
    }
}

package net.mostlyoriginal.api.system.script;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.utils.Array;
import net.mostlyoriginal.api.component.script.Schedule;
import net.mostlyoriginal.api.operation.Operation;

/**
 * Perform scheduled actions.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.script.Schedule
 */
@Wire
public class SchedulerSystem extends EntityProcessingSystem {

    private ComponentMapper<Schedule> sm;

    public SchedulerSystem() {
        super(Aspect.all(Schedule.class));
    }

    @Override
    protected void process(Entity e) {

        Schedule schedule = sm.get(e);
        schedule.age += world.delta;

        final Array<Operation> operations = schedule.steps;
        for (int i = 0; i < operations.size; i++) {
            final Operation operation = operations.get(i);
            if (schedule.age >= operation.getAtAge() && operation.act(world.delta, e) && i < operations.size) {
                operations.removeIndex(i);
                operation.release();
                i--;
                if ( !e.isActive() ) return;
            }
        }

        if (schedule.steps.size == 0 && e.isActive() ) {
            e.edit().remove(Schedule.class);
        }
    }
}

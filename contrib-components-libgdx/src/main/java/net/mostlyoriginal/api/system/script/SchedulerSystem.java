package net.mostlyoriginal.api.system.script;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.utils.Array;
import net.mostlyoriginal.api.component.script.Schedule;
import net.mostlyoriginal.api.step.Step;

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
        super(Aspect.getAspectForAll(Schedule.class));
    }

    @Override
    protected void process(Entity e) {

        Schedule schedule = sm.get(e);
        schedule.age += world.delta;

        final Array<Step> steps = schedule.steps;
        for (int i = 0; i < steps.size; i++) {
            final Step step = steps.get(i);
            if (schedule.age >= step.atAge && step.act(world.delta, e) && i < steps.size) {
                steps.removeIndex(i);
                step.release();
                i--;
                if ( !e.isActive() ) return;
            }
        }

        if (schedule.steps.size == 0 && e.isActive() ) {
            e.removeComponent(Schedule.class).changedInWorld();
        }
    }
}

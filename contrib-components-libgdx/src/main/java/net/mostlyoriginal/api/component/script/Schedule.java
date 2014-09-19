package net.mostlyoriginal.api.component.script;

import com.artemis.Component;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import net.mostlyoriginal.api.step.*;

/**
 * Schedules basic entity transformations.
 *
 * Can be used to schedule things like delayed component addition, entity removal or component removal.
 *
 * entity.addComponent(new Schedule().wait(0.5f).add(new ExampleComponent()).wait(1.5f).remove(ExampleComponent.class).wait(5).deleteFromWorld());
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.system.script.SchedulerSystem
 */
public class Schedule extends Component {

    public Array<Step> steps = new Array<Step>(1);

    public float age;
    private float atAge;

    public Schedule() {
    }

    /**
     * Returns a new or pooled action of the specified type.
     */
    static public <T extends Step> T prepare(Class<T> type, float atAge) {
        Pool<T> pool = Pools.get(type);
        T node = pool.obtain();
        node.setPool(pool);
        node.atAge = atAge;
        return node;
    }

    /** Delay next steps for delaySeconds. */
    public Schedule wait(float delaySeconds)
    {
        this.atAge += delaySeconds;
        return this;
    }

    /** Delete entity from world. */
    public Schedule deleteFromWorld() {
        steps.add(prepare(DeleteFromWorldStep.class, atAge));
        return this;
    }

    /** Add component to entity. Make sure to chain a call to changedInWorld after all your add/remove chains. */
    public Schedule add( final Component component ) {
        AddStep step = prepare(AddStep.class, atAge);
        step.component = component;
        steps.add(step);
        return this;
    }

    /** Remove component from entity. Make sure to chain a call to changedInWorld after all your add/remove chains. */
    public Schedule remove( final Class<? extends Component> component ) {
        RemoveStep step = prepare(RemoveStep.class, atAge);
        step.componentClass = component;
        steps.add(step);
        return this;
    }
}

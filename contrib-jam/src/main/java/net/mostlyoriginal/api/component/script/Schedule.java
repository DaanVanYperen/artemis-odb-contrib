package net.mostlyoriginal.api.component.script;

import com.artemis.Component;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import net.mostlyoriginal.api.component.common.Tweenable;
import net.mostlyoriginal.api.operation.*;

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

    public Array<Operation> steps = new Array<>(4);

    public float age;
    private float atAge;

    public Schedule() {
    }


    /**
     * Returns a new or pooled action of the specified type.
     */
    static public <T extends Operation> T prepare(Class<T> type, float atAge) {
        Pool<T> pool = Pools.get(type);
        T node = pool.obtain();
        node.setPool(pool);
        node.setAtAge(atAge);
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
        steps.add(prepare(DeleteFromWorldOperation.class, atAge));
        return this;
    }

    /**
     * Tween between two component states.
     *
     * @todo lifecycle management of components.
     * @param a component a starting state. Tweening does not release pooled components after use.
     * @param b component b starting state. Tweening does not release pooled components after use.
     * @param duration duration of tween, in seconds.
     * @param interpolation method of interpolation.
     * @return {@code this}
     */
    public <T extends Component & Tweenable> Schedule tween(T a, T b, float duration, Interpolation interpolation) {
        TweenOperation tween = prepare(TweenOperation.class, atAge);
        tween.setup(a, b, interpolation, duration );
        steps.add(tween);
        return this;
    }

    /**
     * Tween between two component states using linear interpolation.
     *
     * @todo lifecycle management of components.
     * @param a component a starting state. Tweening does not release pooled components after use.
     * @param b component b starting state. Tweening does not release pooled components after use.
     * @param duration duration of tween, in seconds.
     * @return {@code this}
     */
    public <T extends Component & Tweenable> Schedule tween(T a, T b, float duration) {
        tween(a,b, duration, Interpolation.linear);
        return this;
    }

    /** Add component to entity. */
    public Schedule add( final Component component ) {
        AddOperation step = prepare(AddOperation.class, atAge);
        step.component = component;
        steps.add(step);
        return this;
    }

    /** Remove component from entity. */
    public Schedule remove( final Class<? extends Component> component ) {
        RemoveOperation step = prepare(RemoveOperation.class, atAge);
        step.componentClass = component;
        steps.add(step);
        return this;
    }
}

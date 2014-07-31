package net.mostlyoriginal.api.step;

import com.artemis.Entity;
import com.badlogic.gdx.utils.Pool;

/**
 * Scriptable step.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.script.Schedule
 */
public abstract class Step implements Pool.Poolable {

    public float atAge;
    private Pool pool;

    protected Step() {}

    abstract public boolean act(float delta, Entity e);

    public void release() {
        if (pool != null) {
            pool.free(this);
            pool = null;
        }
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }
}

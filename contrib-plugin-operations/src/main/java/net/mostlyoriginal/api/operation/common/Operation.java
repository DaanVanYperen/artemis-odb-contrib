package net.mostlyoriginal.api.operation.common;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.utils.Pool;
import net.mostlyoriginal.api.component.Schedule;

/**
 * Scriptable operation.
 *
 * @author Daan van Yperen
 * @see Schedule
 */
public abstract class Operation implements Pool.Poolable {

    protected World world;
    private Pool pool;

    protected Operation() {}

    abstract public boolean act(float delta, Entity e);

    public void setWorld(World world)
    {
        this.world = world;
    }

    @SuppressWarnings("unchecked")
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

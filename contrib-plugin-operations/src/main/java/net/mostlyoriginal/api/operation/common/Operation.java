package net.mostlyoriginal.api.operation.common;

import com.artemis.Entity;
import com.badlogic.gdx.utils.Pool;
import net.mostlyoriginal.api.component.Schedule;

import java.io.Serializable;

/**
 * Scriptable operation.
 *
 * @author Daan van Yperen
 * @see Schedule
 */
public abstract class Operation implements Pool.Poolable, Serializable {

    private transient Pool pool;

    protected Operation() {}

    abstract public boolean process(float delta, Entity e);

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

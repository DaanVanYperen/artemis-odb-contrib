package net.mostlyoriginal.api.operation.common;

import com.artemis.Entity;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
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
	protected boolean completed = false;

	protected Operation() {
	}

	/**
	 * Rewind operation as if it has never been processed yet.
	 * Unlike {@see #reset} this should only clear temporal/completion state.
	 */
	public void rewind() {
		completed = false;
	}

	public void reset() {
		completed=false;
	}

	/**
	 * Perform operation.
	 *
	 * Will do nothing if completed.
	 *
	 * @param delta world delta since last invocation.
	 * @param e subject of operation.
	 * @return {@code true} if completed, {@code false} if not.
	 */
	abstract public boolean process(float delta, Entity e);

	@SuppressWarnings("unchecked")
	public void release() {
		if (pool != null) {
			pool.free(this);
			pool = null;
		}
	}

	/**
	 * Returns a new or pooled action of the specified type.
	 */
	public static <T extends Operation> T prepare(Class<T> type) {
		final Pool<T> pool = Pools.get(type);
		T node = pool.obtain();
		node.setPool(pool);
		return node;
	}

	/**
	 * @return {@code true} if operation completed, {@code false} if operation not completed
	 */
	public final boolean isCompleted() {
		return completed;
	}

	public final Pool getPool() {
		return pool;
	}

	public final void setPool(Pool pool) {
		this.pool = pool;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
}

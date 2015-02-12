package net.mostlyoriginal.api.utils.pooling;

import com.artemis.utils.Bag;
import com.artemis.utils.reflect.ClassReflection;

/**
 * <p>Implementation of Object Pool pattern.</p>
 * <b>Example usage:</p>
 * <pre>   ObjectPool pool = Pools.getPool(ExplosionEvent.class);
 *   Event event = pool.obtain();
 *   pool.free(event);</pre>
 *
 * @author Namek
 * @see #obtain()
 * @see #free(Object)
 */
public abstract class ObjectPool<T> {
	protected final Class<T> type;
	private final Bag<T> pool = new Bag<T>();
	private final boolean typeImplementsPoolable;

	
	public ObjectPool(Class<T> type) {
		if (type == null) {
			throw new RuntimeException("Null given instead of type");
		}
		
		this.type = type;
		typeImplementsPoolable = ClassReflection.isAssignableFrom(Poolable.class, type);
	}

	/**
	 * Gets free object from pool or creates a new one if there are no free objects.
	 * 
	 * @see #free(Object)
	 */
	public T obtain() {
		T obj = !pool.isEmpty()
			? pool.remove(pool.size() - 1)
			: instantiateObject();
		
		return obj;
	}
	
	/**
	 * <p>Puts obtained object back into the pool.</p>
	 * <p>Calls <code>reset()</code> if it's class implements {@link Poolable} interface.</p>
	 * 
	 * @see #obtain()
	 */
	public void free(T obj) {
		pool.add(obj);
		
		if (typeImplementsPoolable) {
			((Poolable) obj).reset();
		}
	}
	
	/**
	 * Clears entire pool.
	 */
	public void clear() {
		pool.clear();
	}
	
	protected abstract T instantiateObject();
}

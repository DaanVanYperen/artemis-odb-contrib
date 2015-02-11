package net.mostlyoriginal.api.utils.pooling;
	
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * <p>Collection of object pools mapped to their object types.</p>
 * <p>Constructor-less usage is available through {@link Pools} which handles <code>PoolsCollection</code> statically.</p>
 * 
 * @author Namek
 * @see Pools
 */
public class PoolsCollection {
	protected final Map<Class<?>, ObjectPool<?>> pools = new IdentityHashMap<Class<?>, ObjectPool<?>>();

	/**
	 * Gets object pool of given type from pools collection.
	 * If pool doesn't exist, ReflectionPool will be created and saved internally.
	 */
	@SuppressWarnings("unchecked")
	public <T> ObjectPool<T> getPool(Class<T> type) {
		ObjectPool<?> pool = (ObjectPool<?>) pools.get(type);
		
		if (pool == null) {
			pool = new ReflectionPool<T>(type);
			pools.put(type, pool);
		}
		
		return (ObjectPool<T>) pool;
	}
	
	/**
	 * Set custom pool. If pool for same type already existed, it will be forgotten.
	 */
	public <T> void setPool(Class<T> type, ObjectPool<T> pool) {
		pools.put(type, pool);
	}
	
	/**
	 * Clears and removes pool of given type from this collection.
	 */
	public void forgetPool(Class<?> type) {
		ObjectPool<?> pool = pools.remove(type);
		
		if (pool != null) {
			pool.clear();
		}
	}
	
	/**
	 * Obtains object from pool of given type. Non-existing pool is being created.
	 * 
	 * @see #getPool(Class)
	 * @see ObjectPool#obtain()
	 */
	public <T> T obtain(Class<T> type) {
		ObjectPool<T> pool = getPool(type);
		return pool.obtain();
	}

	/**
	 * <p>Puts object into the pool.</p>
	 * <p>Calls <code>reset()</code> if it's class implements {@link Poolable} interface.</p>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void free(Object obj) {
		ObjectPool pool = getPool(obj.getClass());
		pool.free(obj);
	}
}

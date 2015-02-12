package net.mostlyoriginal.api.utils.pooling;

/**
 * <p>Handles {@link PoolCollection} statically for constructor-less shorthand usage.</p>
 * <p><b>Usage:</p>
 * <pre>   Event event = Pools.obtain(ExplosionEvent.class);
 *   Pools.free(event);</pre>
 * @author Namek
 * @see PoolCollection
 */
public final class Pools {
	private static final PoolsCollection pools = new PoolsCollection();
	
	public static <T> ObjectPool<T> getPool(Class<T> type) {
		return pools.getPool(type);
	}
	
	public static <T> void setPool(Class<T> type, ObjectPool<T> pool) {
		pools.setPool(type, pool);
	}
	
	public void forgetPool(Class<?> type) {
		pools.forgetPool(type);
	}
	
	public static <T> T obtain(Class<T> type) {
		return pools.obtain(type);
	}
	
	public static void free(Object obj) {
		pools.free(obj);
	}
}

package net.mostlyoriginal.api.utils;

import java.util.IdentityHashMap;
import java.util.Map;

import com.artemis.utils.Bag;
import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.ReflectionException;

public class ReflectionPools {
    private static final Map<Class<?>, Bag<Object>> pools = new IdentityHashMap<Class<?>, Bag<Object>>();
    
    
    @SuppressWarnings("unchecked")
	public static <T> Bag<T> getPool( Class<T> type )
    {
    	Bag<Object> pool = pools.get(type);
		
		if (pool == null) {
			pool = new Bag<Object>();
			pools.put(type, pool);
		}
		
		return (Bag<T>) pool;
    }

	public static <T> T obtain( Class<T> type )
    {
    	Bag<T> pool = getPool(type);

		T event = null;
		try {
			event = !pool.isEmpty()
				? pool.remove(pool.size() - 1)
				: ClassReflection.newInstance(type);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			throw e;
		}
		catch (ReflectionException e) {
			e.printStackTrace();
		}
		
		return event;
    }

    public static void free( Object obj )
    {
    	Bag<Object> pool = pools.get(obj.getClass());
		pool.add(obj);
	}
}

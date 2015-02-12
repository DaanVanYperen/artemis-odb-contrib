package net.mostlyoriginal.api.utils.pooling;

import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.Constructor;
import com.artemis.utils.reflect.ReflectionException;

/**
 * Object Pool that creats objects through Reflection mechanism.
 * 
 * @author Namek
 * @see ObjectPool
 */
public class ReflectionPool<T> extends ObjectPool<T> {
	private final Constructor constructor;
	
	public ReflectionPool(Class<T> type) {
		super(type);
		
		try {
			constructor = ClassReflection.getConstructor(type);
			constructor.setAccessible(true);
		}
		catch (ReflectionException e) {
			String error = "Couldn't find parameterless public constructor for given class type " + type.getName();

			throw new RuntimeException(error, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected T instantiateObject() {
		try {
			return (T) constructor.newInstance();
		}
		catch (ReflectionException e) {
			String error = "Couldn't instantiate object of type " + type.getName();
			throw new RuntimeException(error, e);
		}
	}
}

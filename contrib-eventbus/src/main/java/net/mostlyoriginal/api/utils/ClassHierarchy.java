package net.mostlyoriginal.api.utils;

import com.artemis.utils.Bag;

import java.util.ArrayList;
import java.util.IdentityHashMap;

/**
 * @Author DaanVanYperen
 */
public class ClassHierarchy {

	IdentityHashMap<Class<?>, Class<?>[]> hierarchyCache = new IdentityHashMap<>();

	/** Flatten hierarchy of class, front to back. */
	public Class<?>[] of(Class<?> origin)
	{
		Class<?>[] hierarchy = hierarchyCache.get(origin);
		if ( hierarchy == null )
		{
			hierarchy = getFlatHierarchyUncached(origin);
			hierarchyCache.put(origin, hierarchy);
		}
		return hierarchy;
	}

	/** Flatten hierarchy of class, front to back. Excluding Object. */
	private Class<?>[] getFlatHierarchyUncached(Class<?> origin) {
		ArrayList<Class<?>> result = new ArrayList<>(4);
		Class<?> clazz = origin;
		do {
			result.add(clazz);
		} while ((clazz = clazz.getSuperclass()) != Object.class);

		return result.toArray(new Class<?>[result.size()]);
	}
}

package net.mostlyoriginal.api.utils;

import java.util.IdentityHashMap;

/**
 * Provide unique indices for T.
 *
 * Based on @see SystemIndexManager.
 *
 * @author Daan van Yperen
 */
class IndexManager<T> {

	/** Amount of EntitySystem indices. */
	private int INDEX = 0;

	private final IdentityHashMap<T, Integer> indices = new IdentityHashMap<T, Integer>();

	int getIndexFor(T es) {
		Integer index = indices.get(es);
		if(index == null) {
			index = INDEX++;
			indices.put(es, index);
		}
		return index;
	}
}
package net.mostlyoriginal.api.utils.pooling;

/**
 * Objects of class implementing this interface will have called
 * {@link #reset()} method automatically when passed to {@link ObjectPool#free(Object)}. 
 * 
 * @see ObjectPool#free(Object)
 */
public interface Poolable {
	void reset();
}

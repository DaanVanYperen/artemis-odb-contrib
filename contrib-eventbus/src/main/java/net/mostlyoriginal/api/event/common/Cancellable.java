package net.mostlyoriginal.api.event.common;

/**
 * Implement for cancellable events.
 *
 * @author DaanVanYperen
 */
public interface Cancellable {

	boolean isCancelled();

	void setCancelled(boolean value);
}

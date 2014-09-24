package net.mostlyoriginal.api.event.common;

/**
 * Implement for cancellable events.
 *
 * @author Daan van Yperen
 */
public interface Cancellable {

	boolean isCancelled();

	void setCancelled(boolean value);
}

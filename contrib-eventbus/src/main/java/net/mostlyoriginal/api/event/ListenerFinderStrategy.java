package net.mostlyoriginal.api.event;

import java.util.List;

/**
 * Helper that resolves listener on entity.
 *
 * @author Daan van Yperen
 */
public interface ListenerFinderStrategy {

    /** Find all listeners in o and return as EventListeners. */
    public List<EventListener> resolve( Object o );
}

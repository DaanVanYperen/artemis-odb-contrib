package net.mostlyoriginal.api.network.marshal.common;

/**
 * Contract for marshal observer.
 *
 * @author Daan van Yperen
 */
public interface MarshalObserver {

    /** object received from connection with ID */
    void received(int connectionId, Object object);

    /** connection with ID disconnected */
    void disconnected(int connectionId);

    /** connection with ID connected. */
    void connected(int connectionId);
}

package net.mostlyoriginal.api.network.marshal.common;

/**
 * Marshalling strategy.
 *
 * Implement to provide your own marshaling solutions.
 *
 * @author Daan van Yperen
 */
public interface MarshalStrategy {

    /** Set listener that will handle marshal events. */
    void setListener( MarshalObserver listener );

    /** Register all dictionary classes that might be marshaled. */
    void setDictionary( MarshalDictionary dictionary );

    /** Push the marshaling forward. */
    void update();

    /** Attempt to start marshaling. */
    void start();

    /** Attempt to stop marshaling. */
    void stop();

    MarshalState getState();
}

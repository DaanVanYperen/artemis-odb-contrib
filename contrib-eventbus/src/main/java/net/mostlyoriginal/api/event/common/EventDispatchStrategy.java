package net.mostlyoriginal.api.event.common;

/**
 * Listener registration and event dispatch.
 *
 * Wrapper for the complete listener registration and event dispatching
 * strategy used by {@link EventSystem}.
 *
 * Make sure your strategy supports multiple instances if you want to run
 * multiple artemis worlds in parallel.
 *
 * @author Daan van Yperen
 */
public interface EventDispatchStrategy {

    /** Subscribe listener to events. */
    public void register( EventListener listener );

    /**
     * Dispatch event to registered listeners.
     */
    public void dispatch( Event event );
    
    /** 
     * Dispatch event of given type to registered listeners.
     * 
     * Implementations should assume event is not safe to dispatch
     * until the current artemis system has finished processing.
     */
    public <T extends Event> T dispatch( Class<T> type );
    
    /** Process your own business. */
    public void process( );

}

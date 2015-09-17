package net.mostlyoriginal.api.event.common;

import com.artemis.BaseSystem;
import net.mostlyoriginal.api.event.dispatcher.FastEventDispatcher;

import java.util.List;

/**
 * Listener registration and event dispatch from within artemis.
 *
 * Will scan all systems and managers for @Subscribe annotation
 * at initialization.
 *
 * @author Daan van Yperen
 */
public class EventSystem extends BaseSystem {

    private EventDispatchStrategy dispatcherStrategy;
    private ListenerFinderStrategy listenerFinderStrategy;

    /**
     * Init EventSystem with default strategies.
     */
    public EventSystem()
    {
        this(new FastEventDispatcher(), new SubscribeAnnotationFinder());
    }

    /**
     * Init EventSystem with custom strategies.
     * @param dispatcherStrategy Strategy to use for dispatching events.
     * @param listenerFinderStrategy Strategy to use for finding listeners on objects.
     */
    public EventSystem(EventDispatchStrategy dispatcherStrategy, ListenerFinderStrategy listenerFinderStrategy) {
        this.dispatcherStrategy = dispatcherStrategy;
        this.listenerFinderStrategy = listenerFinderStrategy;
    }

    @Override
    protected void initialize() {
        // register events for all systems and managers.
        registerAllSystemEvents();
    }

    /** Resolve all listeners. */
    protected List<EventListener> resolveListeners( Object o )
    {
        return listenerFinderStrategy.resolve(o);
    }

    /** Register all @Subscribe listeners in passed object (typically system, manager). */
    public void registerEvents( Object o )
    {
        registerAll(resolveListeners(o));
    }

    /**
     * Dispatch event to registered listeners.
     */
	public void dispatch( Event event )
	{
		dispatcherStrategy.dispatch(event);
	}

    /**
     * Queue an event to dispatch synchronously.
     */
	public <T extends Event> T dispatch( Class<T> eventType )
	{
		return dispatcherStrategy.dispatch(eventType);
	}

	@Override
	protected void processSystem( )
	{
		dispatcherStrategy.process();
	}

	/** Register all listeners with the handler. */
    private void registerAll ( List<EventListener> listeners )
    {
        for (EventListener listener : listeners) {
            dispatcherStrategy.register(listener);
        }
    }

    /** Register all systems in this world. */
    private void registerAllSystemEvents( )
    {
        for (BaseSystem entitySystem : world.getSystems()) {
            registerEvents(entitySystem);
        }
    }

}

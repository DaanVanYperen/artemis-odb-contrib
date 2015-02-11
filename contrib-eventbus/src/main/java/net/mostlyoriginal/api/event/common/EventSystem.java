package net.mostlyoriginal.api.event.common;

import com.artemis.EntitySystem;
import com.artemis.Manager;
import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.Bag;

import net.mostlyoriginal.api.event.dispatcher.BasicEventDispatcher;
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
public class EventSystem extends VoidEntitySystem {

    private EventDispatchStrategy dispatcherStrategy;
    private ListenerFinderStrategy listenerFinderStrategy;
    private final Bag<Event> eventQueue = new Bag<Event>();

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
        registerAllManagerEvents();
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
     * Queue an event to dispatch synchronously.
     */
    public void dispatch( Event event )
    {
    	eventQueue.add(event);
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
        for (EntitySystem entitySystem : world.getSystems()) {
            registerEvents(entitySystem);
        }
    }

    /** Register all managers in this world. */
    private void registerAllManagerEvents( )
    {
        for (Manager manager : world.getManagers()) {
            registerEvents(manager);
        }
    }

	@Override
	protected void processSystem()
	{
		Object[] eventsToDispatch = eventQueue.getData();
		
		for (int i = 0, s = eventQueue.size(); i < s; i++) {
			Event event = (Event) eventsToDispatch[i];
			dispatcherStrategy.dispatch(event);
		}
		
		eventQueue.clear();
	}

}

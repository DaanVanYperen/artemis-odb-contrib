package net.mostlyoriginal.api.event;

import com.artemis.EntitySystem;
import com.artemis.Manager;

import java.util.List;

/**
 * Listener registration and event dispatch from within artemis.
 *
 * Will scan all systems and managers for @Subscribe annotation
 * at initialization.
 *
 * @author Daan van Yperen
 */
public class EventManager extends Manager {

    private EventDispatchStrategy dispatcher;

    @Override
    protected void initialize() {
        dispatcher = newDispatcher();
        // register events for all systems and managers.
        registerAllSystemEvents();
        registerAllManagerEvents();
    }

    protected EventDispatchStrategy newDispatcher() {
        return new BasicEventDispatcher();
    }

    /** Resolve all listeners. */
    protected List<EventListener> resolveListeners( Object o )
    {
        return new SubscribeAnnotationFinder().resolve(o);
    }

    /** Register all @Subscribe listeners in passed object (typically system, manager). */
    public void registerEvents( Object o )
    {
        registerAll(resolveListeners(o));
    }

    /** Synchronously dispatch the event */
    public void dispatch( Event event )
    {
        dispatcher.dispatch(event);
    }


    /** Register all listeners with the handler. */
    private void registerAll ( List<EventListener> listeners )
    {
        for (EventListener listener : listeners) {
            dispatcher.register(listener);
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

}

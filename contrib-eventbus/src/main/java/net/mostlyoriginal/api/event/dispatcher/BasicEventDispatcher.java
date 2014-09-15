package net.mostlyoriginal.api.event.dispatcher;

import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.artemis.utils.reflect.ClassReflection;
import net.mostlyoriginal.api.event.common.Event;
import net.mostlyoriginal.api.event.common.EventDispatchStrategy;
import net.mostlyoriginal.api.event.common.EventListener;
import net.mostlyoriginal.api.util.ReflectionHelper;
import net.mostlyoriginal.api.utils.BagUtils;

/**
 * Basic Listener registration and event dispatch.
 *
 * Horribly inefficient, brute force proof of concept
 * event dispatcher. Don't actually use this unless
 * you fire one event per hour!
 *
 * @author Daan van Yperen
 */
public class BasicEventDispatcher implements EventDispatchStrategy {

    final Bag<EventListener> listeners = new Bag<>(128);
	private boolean sortDirty = false;

	/** Subscribe listener to events. */
    @Override
    public void register( EventListener listener )
    {
        if ( !listeners.contains(listener) ) {
	        listeners.add(listener);
	        sortDirty =true;
        }
    }

    /** Dispatch event to registered listeners. */
    @Override
    public void dispatch( Event event )
    {
        if ( event == null ) throw new NullPointerException("Event required.");

        // fetch relevant listeners, sorted by order.
        final ImmutableBag<EventListener> relevantListeners = getRelevantListeners(event);

        // iterate over applicable listeners.
        for(int i=0, s=relevantListeners.size(); i<s; i++ )
        {
            final EventListener listener = relevantListeners.get(i);
            if ( listener != null ) {
                listener.handle(event);
            }
        }

    }

    /** Get listeners that are subscribed to the event. */
    protected ImmutableBag<EventListener> getRelevantListeners(Event event) {

        final Bag<EventListener> relevantListeners = new Bag<>();

	    // resort listeners when we need to.
	    if (sortDirty) {
		    sortDirty=false;
		    BagUtils.sort(listeners);
	    }

	    // who needs efficiency! not us!
        for(int i=0, s=listeners.size(); i<s; i++) {
            final EventListener listener = listeners.get(i);
            if ( listener != null ) {
                if ( canHandle(listener, event))
                {
                    relevantListeners.add(listener);
                }
            }
        }

	    return relevantListeners;
    }


    /** Check if listener can handle event. */
    public boolean canHandle(EventListener listener, Event event) {
        final Class listenerEventType = ReflectionHelper.getFirstParameterType(listener.getMethod());
        return listenerEventType != null && ClassReflection.isAssignableFrom(listenerEventType, event.getClass());
    }
}

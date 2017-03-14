package net.mostlyoriginal.api.event.dispatcher;

import net.mostlyoriginal.api.event.common.Event;

import com.artemis.utils.Bag;
import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.ReflectionException;

/**
 * Polling event dispatcher.
 * 
 * @author Namek
 */
public class PollingEventDispatcher extends FastEventDispatcher {
	private final Bag<Event> eventQueue = new Bag<Event>();
	
	@Override
	public void process() {
		Object[] eventsToDispatch = eventQueue.getData();

		int i = 0;
		int s = eventQueue.size();

		while (i < s) {
			for (; i < s; i++) {
				Event event = (Event) eventsToDispatch[i];
				super.dispatch(event);
			}

			// we may end up having more events to dispatch at this point
			//  - some event handlers could dispatch more events
			s = eventQueue.size();
		}
		
		eventQueue.clear();
	}
	
	public void dispatch(Event event) {
		eventQueue.add(event);
	}
	
	@Override
	public <T extends Event> T dispatch(Class<T> type) {
		T event;
		try {
			event = (T)ClassReflection.newInstance(type);
			this.dispatch(event);
		}
		catch (ReflectionException e) {
			String error = "Couldn't instantiate object of type " + type.getName();
			throw new RuntimeException(error, e);
		}
		
		return event;
	}
}

package net.mostlyoriginal.api.event.dispatcher;

import net.mostlyoriginal.api.event.common.Event;

import com.artemis.utils.Bag;

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
		
		for (int i = 0, s = eventQueue.size(); i < s; i++) {
			Event event = (Event) eventsToDispatch[i];
			super.dispatch(event);
		}
		
		eventQueue.clear();
	}
	
	public void dispatch(Event event) {
		eventQueue.add(event);
	}
}

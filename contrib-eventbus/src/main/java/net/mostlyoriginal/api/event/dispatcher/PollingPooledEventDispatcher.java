package net.mostlyoriginal.api.event.dispatcher;

import com.artemis.utils.Bag;

import net.mostlyoriginal.api.event.common.Event;
import net.mostlyoriginal.api.utils.pooling.PoolsCollection;

/**
 * <p>Polling event dispatcher that cares about garbage collection by using Object Pool pattern.</p>
 * <p><b>Note:</b> remember to manually reset event objects OR use Poolable interface.</p>
 * 
 * @author Namek
 */
public class PollingPooledEventDispatcher extends FastEventDispatcher {
    private final Bag<Event> eventQueue = new Bag<Event>();
    private final PoolsCollection pools = new PoolsCollection();

	@Override
	public void process() {
		Object[] eventsToDispatch = eventQueue.getData();
		
		for (int i = 0, s = eventQueue.size(); i < s; i++) {
			Event event = (Event) eventsToDispatch[i];

			super.dispatch(event);
			pools.free(event);
		}
		
		eventQueue.clear();
	}

	@Override
	public <T extends Event> T dispatch(Class<T> type) {
		T event = pools.obtain(type);
		eventQueue.add(event);

		return event;
	}
}

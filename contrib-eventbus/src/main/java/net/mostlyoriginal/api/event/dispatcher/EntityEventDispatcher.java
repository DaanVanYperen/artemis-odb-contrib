package net.mostlyoriginal.api.event.dispatcher;

import com.artemis.utils.Bag;
import net.mostlyoriginal.api.event.common.Event;
import net.mostlyoriginal.api.event.common.EventListener;

/**
 * Fast entity event dispatcher.
 *
 * @author Greg Hibberd
 */
public class EntityEventDispatcher extends FastEventDispatcher {

	@Override
	public void dispatch(Object... args) {
		if(args.length == 1) throw new UnsupportedOperationException("This dispatcher doesn't dispatch events without ids!");
		if(!(args[0] instanceof Integer || args[1] instanceof Event)) throw new IllegalArgumentException("This dispatcher only supports (entityId, Event) arguments!");

		final Bag<EventListener> listeners = getListenersForHierarchical(((Event) args[1]).getClass());

		/** Fetch hierarchical list of listeners. */
		Object[] data = listeners.getData();
		for (int i = 0, s = listeners.size(); i < s; i++) {
			final EventListener listener = (EventListener) data[i];
			listener.handle(args);
		}
	}

	@Override
	public <T extends Event> T dispatch(Class<T> type) {
		throw new UnsupportedOperationException("This dispatcher doesn't dispatch events by type!");
	}

}

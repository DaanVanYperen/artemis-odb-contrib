package net.mostlyoriginal.api.event.dispatcher;

import com.artemis.utils.Bag;
import net.mostlyoriginal.api.event.common.Event;
import net.mostlyoriginal.api.event.common.EventDispatchStrategy;
import net.mostlyoriginal.api.event.common.EventListener;
import net.mostlyoriginal.api.util.ClassHierarchy;

import java.util.IdentityHashMap;

/**
 * Faster event dispatcher.
 *
 * @Author DaanVanYperen
 */
public class FastEventDispatcher implements EventDispatchStrategy {

	final ClassHierarchy classHierarchy = new ClassHierarchy();

	final IdentityHashMap<Class<?>, Bag<EventListener>> listenerCache = new IdentityHashMap<>();
	final IdentityHashMap<Class<?>, Bag<EventListener>> hierarchicalListenerCache = new IdentityHashMap<>();

	@Override
	public void register(EventListener listener) {
		if ( listener == null ) throw new NullPointerException("Listener required.");

		// Bind listener to the related event class.
		Bag<EventListener> listenersFor = getListenersFor(listener.getParameterType(), true);
		if ( !listenersFor.contains(listener)) {
			listenersFor.add(listener);
			// the hierarchical cache is now out of date. purrrrrrrrge!
			invalidateHierarchicalCache();
		}

	}

	private void invalidateHierarchicalCache() {
		if ( hierarchicalListenerCache.size() > 0 ) {
			hierarchicalListenerCache.clear();
		}
	}

	/**
	 * Get listeners for class (non hierarical).
	 *
	 * @param aClass Class to fetch listeners for.
	 * @param createIfMissing instance empty bag when not exist.
	 * @return Listener, or <code>null</code> if missing and not allowed to create.
	 */
	protected Bag<EventListener> getListenersFor(Class<?> aClass, boolean createIfMissing) {
		Bag<EventListener> listeners = listenerCache.get(aClass);
		if (listeners == null && createIfMissing) {
			// if listener is missing, prep an empty bag.
			listeners = new Bag<>(4);
			listenerCache.put(aClass, listeners);
		}
		return listeners;
	}

	/**
	 * Get listeners for class, including all superclasses.
	 * Backed by cache.
	 *
	 * @param aClass Class to fetch listeners for.
	 * @return Bag of listeners, empty if none found.
	 */
	protected Bag<EventListener> getListenersForHierarchical(Class<?> aClass) {
		Bag<EventListener> listeners = hierarchicalListenerCache.get(aClass);
		if (listeners == null) {
			listeners = getListenersForHierarchicalUncached(aClass);
			hierarchicalListenerCache.put(aClass, listeners);
		}
		return listeners;
	}

	/**
	 * Get listeners for class, including all superclasses.
	 * Not backed by cache.
	 *
	 * @param aClass Class to fetch listeners for.
	 * @return Bag of listeners, empty if none found.
	 */
	private Bag<EventListener> getListenersForHierarchicalUncached(Class<?> aClass) {

		// get hierarchy for event.
		final Class<?>[] classes = classHierarchy.of(aClass);

		// step through hierarchy back to front, fetching the listeners for each step.
		final Bag<EventListener> hierarchicalListeners = new Bag<>(4);
		for (Class<?> c : classes) {
			final Bag<EventListener> listeners = getListenersFor(c, false);
			if (listeners != null) {
				hierarchicalListeners.addAll(listeners);
			}
		}

		return hierarchicalListeners;
	}

	@Override
	public void dispatch(Event event) {
		if ( event == null ) throw new NullPointerException("Event required.");

		final Bag<EventListener> listeners = getListenersForHierarchical(event.getClass());

		/** Fetch hierarchical list of listeners. */
		Object[] data = listeners.getData();
		for (int i = 0, s = listeners.size(); i < s; i++) {
			final EventListener listener = (EventListener) data[i];
			if (listener != null) {
				listener.handle(event);
			}
		}
	}
}

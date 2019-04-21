package net.mostlyoriginal.api.event.dispatcher;

import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.Method;
import net.mostlyoriginal.api.event.common.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Daan on 14-9-2014.
 * Extended by Greg on 21-04-2019
 */
public class EntityEventDispatcherTest {

	protected EventDispatchStrategy createDispatcherInstance() {
		return new EntityEventDispatcher();
	}

	public EventDispatchStrategy dispatcher;

	@Before
	public void setUp() throws Exception {
		dispatcher = createDispatcherInstance();
	}

	public static class CancellableEvent implements Event, Cancellable {
		private boolean cancelled;

		@Override
		public boolean isCancelled() {
			return cancelled;
		}

		@Override
		public void setCancelled(boolean value) {

			cancelled = value;
		}
	}
	public static class BaseEvent implements Event {}
	public static class ExtendedEvent extends BaseEvent {}
	public static class MismatchedEvent implements Event {}

	public static class SingleListenPojo {
		public int calls=0;
		public void l(int entityId, BaseEvent event) {
			calls++;
		}
	}

	/** Setup a listener pojo, registering all methods as listeners. */
	protected <T> T setupListenerPojo(Class<T> pojoClass)  {
		try {
			final T pojo = pojoClass.newInstance();

			for (Method method : ClassReflection.getMethods(pojoClass)) {

				// do not register superclass methods.
				if ( !method.getDeclaringClass().equals(pojoClass) )
					continue;
				dispatcher.register(new EventListener(pojo, method));
			}
			return pojo;
		} catch ( Exception e )
		{
			throw new RuntimeException("Could not setup listener pojo",e);
		}
	}

	@Test
	public void Dispatch_EventWithExactClassListener_ListenerReceivesEvent() {
		final SingleListenPojo pojo = setupListenerPojo(SingleListenPojo.class);
		// match on exact listener class.
		dispatch(new BaseEvent());
		assertEquals(1, pojo.calls);
	}

	/** Dispatch wrapper. */
	protected void dispatch(Event event) {
		dispatch(0, event);
	}

	protected void dispatch(int entityId, Event event) {
		dispatcher.dispatch(entityId, event);
	}

	@Test
	public void Dispatch_EventWithSuperclassListener_ListenerReceivesEvent() {
		final SingleListenPojo pojo = setupListenerPojo(SingleListenPojo.class);
		dispatch(new ExtendedEvent());
		assertEquals(1, pojo.calls);
	}

	@Test
	public void Dispatch_LateRegisteredListener_NoRegistrationIssues() {
		// Make sure registering a listener late doesn't break the dispatchers.
		final SingleListenPojo pojo = setupListenerPojo(SingleListenPojo.class);
		dispatch(new ExtendedEvent());
		final SingleListenPojo pojo2 = setupListenerPojo(SingleListenPojo.class);
		dispatch(new ExtendedEvent());
		assertEquals(2, pojo.calls);
		assertEquals(1, pojo2.calls);
	}

	@Test
	public void Dispatch_RegisterListenerTwice_NotCalledTwice() {

		// Create doubled up event listeners.
		final SingleListenPojo pojo = new SingleListenPojo();
		for (Method method : ClassReflection.getMethods(SingleListenPojo.class)) {

			// do not register superclass methods.
			if ( !method.getDeclaringClass().equals(SingleListenPojo.class) )
				continue;

			// register methods twice.
			EventListener listener = new EventListener(pojo, method);
			dispatcher.register(listener);
			dispatcher.register(listener);
		}

		dispatch(new ExtendedEvent());
		assertEquals(1, pojo.calls);
	}

	@Test
	public void Dispatch_MismatchingEvents_ListenerDoesNotReceiveEvent() {
		final SingleListenPojo pojo = setupListenerPojo(SingleListenPojo.class);
		// mismatched event has no listeners.
		dispatch(new MismatchedEvent());
		assertEquals(0, pojo.calls);
	}

	public static class MultiListenPojo {
		public int calls1=0;
		public int calls2=0;
		public int calls3=0;
		public void l(int entityId, BaseEvent event) {
			calls1++;
		}
		public void l2(int entityId, BaseEvent event) {
			calls2++;
		}
		public void l3(int entityId, ExtendedEvent event) {
			calls3++;
		}
	}

	@Test
	public void Dispatch_SeveralMatchingListeners_AllListenersCalled() {
		final MultiListenPojo pojo = setupListenerPojo(MultiListenPojo.class);
		dispatch(new ExtendedEvent());
		// all listeners should be hit, even superclass ones.
		assertEquals(1, pojo.calls1);
		assertEquals(1, pojo.calls2);
		assertEquals(1, pojo.calls3);
	}

	@Test
	public void Dispatch_PrioritizedListeners_CalledInCorrectOrder() {

		class SequenceListen {
			public int lastCalls = 0;
			public int firstCalls = 0;
			public int middleCalls = 0;

			@Subscribe(priority = -5)
			public void last(int entityId, BaseEvent event) {
				assertEquals( "last - first not called before us.", 1, firstCalls );
				assertEquals( "last - middle not called before us.", 1, middleCalls );
				lastCalls++;
			}

			@Subscribe(priority = 5)
			public void first(int entityId, BaseEvent event) {
				assertEquals( "first - middle called before us.", 0, middleCalls );
				assertEquals( "first - last called before us.", 0, lastCalls );
				firstCalls++;
			}

			@Subscribe
			public void middle(int entityId, BaseEvent event) {
				assertEquals( "middle - first method not called.", 1, firstCalls );
				assertEquals( "middle - last called before us.", 0, lastCalls );
				middleCalls++;
			}
		}

		final SequenceListen pojo = new SequenceListen();
		final List<EventListener> listeners = new SubscribeAnnotationFinder().resolve(pojo);

		for (EventListener listener : listeners) {
			dispatcher.register(listener);
		}

		dispatch(new BaseEvent());

		// asserts are run in the SequenceListen class.
		// we just have to make sure the final class has also been called.
		assertEquals("last method never called.", 1, pojo.firstCalls);
		assertEquals("middle method never called.", 1, pojo.middleCalls);
		assertEquals("last method never called.", 1, pojo.lastCalls);
	}

	@Test
	public void Dispatch_PrioritizedListenersCancelledEvent_CancelledProperly() {
		class CancelListener {
			private int calledCancelled =0;

			@Subscribe(priority = 3, ignoreCancelledEvents = true) public void called(int entityId, CancellableEvent event) { }
			@Subscribe(priority = 2, ignoreCancelledEvents = true) public void cancelling(int entityId, CancellableEvent event) { event.setCancelled(true); }
			@Subscribe(priority = 1, ignoreCancelledEvents = true) public void ignoreCancelled(int entityId, CancellableEvent event) {
				fail("Should never be called");
			}
			@Subscribe(priority = 0) public void dontIgnoreCancelled(int entityId, CancellableEvent event) { calledCancelled++; }
		}

		final CancelListener pojo = new CancelListener();
		final List<EventListener> listeners = new SubscribeAnnotationFinder().resolve(pojo);

		for (EventListener listener : listeners) {
			dispatcher.register(listener);
		}
		dispatch(new CancellableEvent());

		// expect cancelled events to be properly called.
		assertEquals(1, pojo.calledCancelled);
	}

	public static class UnrelatedEvent1 implements Event {}
	public static class UnrelatedEvent2 implements Event {}
	public static class UnrelatedEvent3 implements Event {}

	@Test
	public void Dispatch_SeveralEventHandlers_CorrectlyCascadedEventDispatch() {
		class MultiEventHandler {
			boolean handler1Called = false;
			boolean handler2Called = false;
			boolean handler3Called = false;

			@Subscribe()
			public void handler1(int entityId, UnrelatedEvent1 evt) {
				handler1Called = true;
				dispatch(new UnrelatedEvent2());
			}

			@Subscribe()
			public void handler2(int entityId, UnrelatedEvent2 evt) {
				handler2Called = true;
				dispatch(new UnrelatedEvent3());
			}

			@Subscribe()
			public void handler3(int entityId, UnrelatedEvent3 evt) {
				handler3Called = true;
			}
		}

		final MultiEventHandler handler = new MultiEventHandler();
		final List<EventListener> listeners = new SubscribeAnnotationFinder().resolve(handler);

		for (EventListener listener : listeners) {
			dispatcher.register(listener);
		}
		dispatch(new UnrelatedEvent1());

		// expect that all event handlers were called in cascade aftet dispatch the first one
		assert(handler.handler1Called);
		assert(handler.handler2Called);
		assert(handler.handler3Called);
	}


	public static class EntityListenPojo {
		public int entity1=0;
		public int entity2=0;
		public int entity3=0;
		public void l(int entityId, BaseEvent event) {
			entity1 = entityId;
		}
		public void l2(int entityId, ExtendedEvent event) {
			entity2 = entityId;
		}
		public void l2(int entityId, MismatchedEvent event) {
			entity3 = entityId;
		}
	}

	@Test
	public void Dispatch_SeveralMatchingListeners_DifferentEntities_AllListenersCalled() {
		final EntityListenPojo pojo = setupListenerPojo(EntityListenPojo.class);
		dispatch(0, new BaseEvent());
		dispatch(1, new ExtendedEvent());
		dispatch(2, new MismatchedEvent());
		// all listeners should be hit, even superclass ones.
		assertEquals(1, pojo.entity1);
		assertEquals(1, pojo.entity2);
		assertEquals(2, pojo.entity3);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void Dispatch_ShouldNotDispatchClassEvents()
	{
		dispatcher.dispatch(BaseEvent.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void Dispatch_ShouldNotDispatchIllegalClassEvents()
	{
		dispatcher.dispatch(BaseEvent.class, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void Dispatch_ShouldNotDispatchIllegalPrimativeEvents()
	{
		dispatcher.dispatch("RuhRoh", BaseEvent.class);
	}
}

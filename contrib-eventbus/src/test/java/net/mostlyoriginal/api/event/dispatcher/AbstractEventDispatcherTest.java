package net.mostlyoriginal.api.event.dispatcher;

import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.Method;
import net.mostlyoriginal.api.event.common.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public abstract class AbstractEventDispatcherTest {

    public EventDispatchStrategy dispatcher;

    @Before
    public void setUp() throws Exception {
        dispatcher = createDispatcherInstance();
    }

	protected abstract EventDispatchStrategy createDispatcherInstance();

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
        public void l(BaseEvent event) {
            calls++;
        }
    }

    /** Setup a listener pojo, registering all methods as listeners. */
    private <T> T setupListenerPojo( Class<T> pojoClass )  {
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
        dispatcher.dispatch(new BaseEvent());
        assertEquals(1, pojo.calls);
    }

    @Test
    public void Dispatch_EventWithSuperclassListener_ListenerReceivesEvent() {
        final SingleListenPojo pojo = setupListenerPojo(SingleListenPojo.class);
        dispatcher.dispatch(new ExtendedEvent());
        assertEquals(1, pojo.calls);
    }

	@Test
	public void Dispatch_LateRegisteredListener_NoRegistrationIssues() {
		// Make sure registering a listener late doesn't break the dispatchers.
		final SingleListenPojo pojo = setupListenerPojo(SingleListenPojo.class);
		dispatcher.dispatch(new ExtendedEvent());
		final SingleListenPojo pojo2 = setupListenerPojo(SingleListenPojo.class);
		dispatcher.dispatch(new ExtendedEvent());
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

		dispatcher.dispatch(new ExtendedEvent());
		assertEquals(1, pojo.calls);
	}

    @Test
    public void Dispatch_MismatchingEvents_ListenerDoesNotReceiveEvent() {
        final SingleListenPojo pojo = setupListenerPojo(SingleListenPojo.class);
        // mismatched event has no listeners.
        dispatcher.dispatch(new MismatchedEvent());
        assertEquals(0, pojo.calls);
    }

    public static class MultiListenPojo {
        public int calls1=0;
        public int calls2=0;
        public int calls3=0;
        public void l(BaseEvent event) {
            calls1++;
        }
        public void l2(BaseEvent event) {
            calls2++;
        }
        public void l3(ExtendedEvent event) {
            calls3++;
        }
    }

    @Test
    public void Dispatch_SeveralMatchingListeners_AllListenersCalled() {
        final MultiListenPojo pojo = setupListenerPojo(MultiListenPojo.class);
	    dispatcher.dispatch(new ExtendedEvent());
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
			public void last(BaseEvent event) {
				assertEquals( "last - first not called before us.", 1, firstCalls );
				assertEquals( "last - middle not called before us.", 1, middleCalls );
				lastCalls++;
			}

			@Subscribe(priority = 5)
			public void first(BaseEvent event) {
				assertEquals( "first - middle called before us.", 0, middleCalls );
				assertEquals( "first - last called before us.", 0, lastCalls );
				firstCalls++;
			}

			@Subscribe
			public void middle(BaseEvent event) {
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

		dispatcher.dispatch(new BaseEvent());

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

			@Subscribe(priority = 3, ignoreCancelledEvents = true) public void called(CancellableEvent event) { }
			@Subscribe(priority = 2, ignoreCancelledEvents = true) public void cancelling(CancellableEvent event) { event.setCancelled(true); }
			@Subscribe(priority = 1, ignoreCancelledEvents = true) public void ignoreCancelled(CancellableEvent event) {
				fail("Should never be called");
			}
			@Subscribe(priority = 0) public void dontIgnoreCancelled(CancellableEvent event) { calledCancelled++; }
		}

		final CancelListener pojo = new CancelListener();
		final List<EventListener> listeners = new SubscribeAnnotationFinder().resolve(pojo);

		for (EventListener listener : listeners) {
			dispatcher.register(listener);
		}
		dispatcher.dispatch(new CancellableEvent());

		// expect cancelled events to be properly called.
		assertEquals(1, pojo.calledCancelled);
	}

}
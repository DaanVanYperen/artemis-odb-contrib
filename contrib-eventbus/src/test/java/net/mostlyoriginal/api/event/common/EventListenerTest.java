package net.mostlyoriginal.api.event.common;

import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.Method;
import com.artemis.utils.reflect.ReflectionException;
import org.junit.Assert;
import org.junit.Test;

public class EventListenerTest {

	private static class CancellableEvent implements Event, Cancellable {
		private boolean cancelled;

		private CancellableEvent(boolean cancelled) {
			this.cancelled = cancelled;
		}

		@Override
		public boolean isCancelled() {
			return cancelled;
		}

		@Override
		public void setCancelled(boolean value) {
			cancelled = value;
		}
	}
	private static class BasicEvent implements Event {}

    @Test(expected = IllegalArgumentException.class)
    public void Instance_MethodWithoutAnyParameter_AbortWithException() throws ReflectionException {
        class ListenerPojo {
            public void invalidListener() {
            }
        }
        ListenerPojo pojo = new ListenerPojo();
        new EventListener(pojo, findMethod(pojo, "invalidListener"));
    }

	@Test
	public void Instance_MethodWithEventSubclassParameter_NoException() throws ReflectionException {
		class ListenerPojo {
			public void validListener(BasicEvent event) {
			}
		}
		ListenerPojo pojo = new ListenerPojo();
		new EventListener(pojo, findMethod(pojo, "validListener", BasicEvent.class));
		// no exceptions? victory!
	}

	@Test(expected = IllegalArgumentException.class)
	public void Instance_MethodWithEventInterfaceParameter_Exception() throws ReflectionException {
		class ListenerPojo {
			public void validListener(Event event) {
			}
		}
		ListenerPojo pojo = new ListenerPojo();
		new EventListener(pojo, findMethod(pojo, "validListener", Event.class));
		// Event is an interface and cannot be called.
	}


    @Test(expected = IllegalArgumentException.class)
    public void Instance_MethodWithoutEventParameter_AbortWithException() throws ReflectionException {
        class NotAnEvent {
        }
        class ListenerPojo {
            public void invalidListener(NotAnEvent o) {
            }
        }
        ListenerPojo pojo = new ListenerPojo();
        new EventListener(pojo, findMethod(pojo, "invalidListener", NotAnEvent.class));
    }


    @Test(expected = ReflectionException.class)
    public void Instance_MethodWithWrongEventParameters_AbortWithException() throws ReflectionException {
        class ListenerPojo {
            public void invalidListener(BasicEvent o, Object o2) {
            }
        }
        ListenerPojo pojo = new ListenerPojo();
        new EventListener(pojo, findMethod(pojo, "invalidListener", Object.class, BasicEvent.class));
    }

	@Test
	public void CancelledEvent_ListenerIgnoringCancelled_IgnoresCancelled() throws ReflectionException {
		class ListenerPojo {
			public void neverCall(CancellableEvent event) {
				Assert.fail();
			}
		}
		ListenerPojo pojo = new ListenerPojo();
		new EventListener(pojo, findMethod(pojo, "neverCall", CancellableEvent.class),0,true).handle(new CancellableEvent(true));
	}

	@Test
	public void CancelledEvent_ListenerAcceptingCancelled_AcceptsCancelled() throws ReflectionException {
		class ListenerPojo {
			int count=0;
			public void call(CancellableEvent event) {
				count++;
			}
		}
		ListenerPojo pojo = new ListenerPojo();
		new EventListener(pojo, findMethod(pojo, "call", CancellableEvent.class),0,false).handle(new CancellableEvent(true));
		Assert.assertEquals(1, pojo.count);
	}


	@Test
	public void NotCancelledEvent_ListenerIgnoringCancelled_AcceptsNotCancelled() throws ReflectionException {
		class ListenerPojo {
			int count=0;
			public void call(CancellableEvent event) {
				count++;
			}
		}
		ListenerPojo pojo = new ListenerPojo();
		new EventListener(pojo, findMethod(pojo, "call", CancellableEvent.class),0,true).handle(new CancellableEvent(false));
		Assert.assertEquals(1, pojo.count);
	}

	@Test
	public void NotCancelledEvent_ListenerAcceptingCancelled_AcceptsNotCancelled() throws ReflectionException {
		class ListenerPojo {
			int count=0;
			public void call(CancellableEvent event) {
				count++;
			}
		}
		ListenerPojo pojo = new ListenerPojo();
		new EventListener(pojo, findMethod(pojo, "call", CancellableEvent.class),0,false).handle(new CancellableEvent(false));
		Assert.assertEquals(1, pojo.count);
	}


	private Method findMethod(Object o, String methodName, Class... args) throws ReflectionException {
        return ClassReflection.getMethod(o.getClass(), methodName, args);
    }
}
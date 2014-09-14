package net.mostlyoriginal.api.event.common;

import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.Method;
import com.artemis.utils.reflect.ReflectionException;
import net.mostlyoriginal.api.event.common.Event;
import net.mostlyoriginal.api.event.common.EventListener;
import org.junit.Test;

public class EventListenerTest {

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


    @Test(expected = IllegalArgumentException.class)
    public void Instance_MethodWithTooManyEventParameters_AbortWithException() throws ReflectionException {
        class ListenerPojo {
            public void invalidListener(BasicEvent o, Object o2) {
            }
        }
        ListenerPojo pojo = new ListenerPojo();
        new EventListener(pojo, findMethod(pojo, "invalidListener", BasicEvent.class, Object.class));
    }

    private Method findMethod(Object o, String methodName, Class... args) throws ReflectionException {
        return ClassReflection.getMethod(o.getClass(), methodName, args);
    }
}
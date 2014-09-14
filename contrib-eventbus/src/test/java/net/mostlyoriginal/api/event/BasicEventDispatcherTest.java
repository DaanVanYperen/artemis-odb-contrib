package net.mostlyoriginal.api.event;

import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.Method;
import net.mostlyoriginal.api.event.common.Event;
import net.mostlyoriginal.api.event.common.EventListener;
import net.mostlyoriginal.api.event.dispatcher.BasicEventDispatcher;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BasicEventDispatcherTest {

    public BasicEventDispatcher dispatcher;

    @Before
    public void setUp() throws Exception {
        dispatcher = new BasicEventDispatcher();
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
}
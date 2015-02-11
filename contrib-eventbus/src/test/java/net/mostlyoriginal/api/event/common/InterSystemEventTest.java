package net.mostlyoriginal.api.event.common;

import com.artemis.World;
import com.artemis.annotations.Wire;
import com.artemis.systems.VoidEntitySystem;
import net.mostlyoriginal.api.event.common.Event;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test event dispatching between systems.
 *
 * @author Daan van Yperen
 */
public class InterSystemEventTest {

    public static class MyEvent implements Event {
    }

    @Wire
    public static class DispatchSystem extends VoidEntitySystem {

        EventSystem eventManager;

        @Override
        protected void processSystem() {
            eventManager.dispatch(new MyEvent());
        }
    }


    public static class ReceiveSystem extends VoidEntitySystem {
        public int count = 0;

        @Override
        protected void processSystem() {
        }

        @Subscribe
        public void testEvent(MyEvent event) {
            count++;
        }
    }

    @Test
    public void Dispatch_OneListeningSystem_SystemReceivesEvent() {
        World w = new World();
        final EventSystem eventManager = new EventSystem();
        w.setSystem(eventManager);
        ReceiveSystem s1 = new ReceiveSystem();
        w.setSystem(s1);
        w.setSystem(new DispatchSystem());
        w.initialize();
        w.process();
        assertEquals(1, s1.count);
    }

    @Test
    public void Dispatch_NoListeningSystem_NoExceptions() {
        World w = new World();
        final EventSystem eventManager = new EventSystem();
        w.setSystem(eventManager);
        w.setSystem(new DispatchSystem());
        w.initialize();
        w.process();
        // no exception = success
    }

    @Test
    public void Dispatch_TwoListeningSystem_BothCalled() {
        World w = new World();
        final EventSystem eventManager = new EventSystem();
        w.setSystem(eventManager);
        ReceiveSystem s1 = new ReceiveSystem();
        w.setSystem(s1);
        ReceiveSystem s2 = new ReceiveSystem();
        w.setSystem(s2);
        w.setSystem(new DispatchSystem());
        w.initialize();
        w.process();
        // no exception = success
        assertEquals(1, s1.count);
        assertEquals(1, s2.count);
    }
}
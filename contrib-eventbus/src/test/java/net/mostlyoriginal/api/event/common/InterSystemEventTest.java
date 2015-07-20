package net.mostlyoriginal.api.event.common;

import com.artemis.BaseSystem;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.annotations.Wire;
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
    public static class DispatchSystem extends BaseSystem {

        EventSystem eventManager;

        @Override
        protected void processSystem() {
            eventManager.dispatch(new MyEvent());
        }
    }


    public static class ReceiveSystem extends BaseSystem {
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
        WorldConfiguration config = new WorldConfiguration();
        final EventSystem eventManager = new EventSystem();
        config.setSystem(eventManager);
        ReceiveSystem s1 = new ReceiveSystem();
        config.setSystem(s1);
        config.setSystem(new DispatchSystem());
        World w = new World(config);
        w.process();
        assertEquals(1, s1.count);
    }

    @Test
    public void Dispatch_NoListeningSystem_NoExceptions() {
        WorldConfiguration config = new WorldConfiguration();
        final EventSystem eventManager = new EventSystem();
        config.setSystem(eventManager);
        config.setSystem(new DispatchSystem());
        World w = new World(config);
        w.process();
        // no exception = success
    }

    @Test
    public void Dispatch_TwoListeningSystem_BothCalled() {
        WorldConfiguration config = new WorldConfiguration();
        final EventSystem eventManager = new EventSystem();
        config.setSystem(eventManager);
        ReceiveSystem s1 = new ReceiveSystem();
        config.setSystem(s1);
        ReceiveSystem s2 = new ReceiveSystem();
        config.setSystem(s2);
        config.setSystem(new DispatchSystem());

        World w = new World(config);
        w.process();

        // no exception = success
        assertEquals(1, s1.count);
        assertEquals(1, s2.count);
    }
}
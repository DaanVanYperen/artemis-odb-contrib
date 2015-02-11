package net.mostlyoriginal.api.event.common;

import com.artemis.Manager;
import com.artemis.World;
import com.artemis.systems.VoidEntitySystem;
import net.mostlyoriginal.api.event.common.Event;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Daan van Yperen
 */
public class EventManagerTest {

    public World w;

    @Before
    public void setUp() throws Exception {
        w = new World();
        w.setSystem(new EventSystem());
    }

    @Test
    public void Initialization_NoManagersNoSystems_NoExceptions() {
        w.initialize();
        // no exception is good!
    }

	public static class BasicEvent implements Event {
	}
	public static class SimpleEvent extends BasicEvent {
	}

    public static class SimplePojo {
        public int count;

        @Subscribe
        public void testListener(BasicEvent event) {
            count++;
        }
    }

    public static class SimpleManager extends Manager {
        public int count;

        @Subscribe
        public void testListener(BasicEvent event) {
            count++;
        }
    }

    public static class SimpleEntitySystem extends VoidEntitySystem {
        public int count;

        @Subscribe
        public void testListener(BasicEvent event) {
            count++;
        }

        @Override
        protected void processSystem() {
        }
    }

    @Test
    public void Initialization_ManagerListeners_AllListenersAutoRegistered() {
        SimpleManager m1 = new SimpleManager();
        SimpleManager m2 = new SimpleManager();
        w.setManager(m1);
        w.setManager(m2);
        w.initialize();
        w.getSystem(EventSystem.class).dispatch(new SimpleEvent());

        assertEquals(1, m1.count);
        assertEquals(1, m2.count);
    }

    @Test
    public void Initialization_SystemListeners_AllListenersAutoRegistered() {
        SimpleEntitySystem es1 = new SimpleEntitySystem();
        SimpleEntitySystem es2 = new SimpleEntitySystem();
        w.setSystem(es1);
        w.setSystem(es2);
        w.initialize();
        w.getSystem(EventSystem.class).dispatch(new SimpleEvent());

        assertEquals(1, es1.count);
        assertEquals(1, es2.count);
    }

    @Test
    public void Registration_AllTypesWithNoListeners_NoExceptions() {
        w.setSystem(new VoidEntitySystem() {
            @Override
            protected void processSystem() {
            }
        });
        w.setManager(new Manager() {
        });
        w.initialize();
        w.getSystem(EventSystem.class).registerEvents(new Object() {});
        w.getSystem(EventSystem.class).dispatch(new SimpleEvent());
        // no exception? happy!
    }

    @Test
    public void PojoRegistration_PojoWithListeners_ListenersRegistered() {
        w.initialize();
        SimplePojo pojo = new SimplePojo();
        w.getSystem(EventSystem.class).registerEvents(pojo);
        w.getSystem(EventSystem.class).dispatch(new SimpleEvent());
        assertEquals(1, pojo.count);
    }
}

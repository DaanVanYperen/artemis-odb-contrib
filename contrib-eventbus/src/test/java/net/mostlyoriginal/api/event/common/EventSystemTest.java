package net.mostlyoriginal.api.event.common;

import com.artemis.BaseSystem;
import com.artemis.Manager;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Daan van Yperen
 */
public class EventSystemTest {

    public WorldConfiguration config;

    @Before
    public void setUp() throws Exception {
        config = new WorldConfiguration();
        config.setSystem(new EventSystem());
    }

    @Test
    public void Initialization_NoManagersNoSystems_NoExceptions() {
        new World(config);
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

    public static class SimpleEntitySystem extends BaseSystem {
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
        config.setManager(m1);
        config.setManager(m2);
        final World w = new World(config);
        w.getSystem(EventSystem.class).dispatch(new SimpleEvent());

        assertEquals(1, m1.count);
        assertEquals(1, m2.count);
    }

    @Test
    public void Initialization_SystemListeners_AllListenersAutoRegistered() {
        SimpleEntitySystem es1 = new SimpleEntitySystem();
        SimpleEntitySystem es2 = new SimpleEntitySystem();
        config.setSystem(es1);
        config.setSystem(es2);
        final World w = new World(config);
        w.getSystem(EventSystem.class).dispatch(new SimpleEvent());

        assertEquals(1, es1.count);
        assertEquals(1, es2.count);
    }

    @Test
    public void Registration_AllTypesWithNoListeners_NoExceptions() {
        config.setSystem(new BaseSystem() {
            @Override
            protected void processSystem() {
            }
        });
        config.setManager(new Manager() {
        });
        final World w = new World(config);
        w.getSystem(EventSystem.class).registerEvents(new Object() {});
        w.getSystem(EventSystem.class).dispatch(new SimpleEvent());
        // no exception? happy!
    }

    @Test
    public void PojoRegistration_PojoWithListeners_ListenersRegistered() {
        final World w = new World(config);
        SimplePojo pojo = new SimplePojo();
        w.getSystem(EventSystem.class).registerEvents(pojo);
        w.getSystem(EventSystem.class).dispatch(new SimpleEvent());
        assertEquals(1, pojo.count);
    }
}

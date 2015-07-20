package net.mostlyoriginal.gwt;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.google.gwt.junit.client.GWTTestCase;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.gwt.system.DispatchTestSystem;
import net.mostlyoriginal.gwt.system.ReceiveTestSystem;

/**
 * Test event dispatching between systems on GWT.
 *
 * @author Daan van Yperen
 */
public class InterSystemEventGwtTest  extends GWTTestCase {

    @Override
    public String getModuleName() {
        return "net.mostlyoriginal.ContribTest";
    }

    public void test_Dispatch_OneListeningSystem_SystemReceivesEvent() {
        WorldConfiguration config = new WorldConfiguration();
        final EventSystem eventManager = new EventSystem();
        config.setSystem(eventManager);
        ReceiveTestSystem s1 = new ReceiveTestSystem();
        config.setSystem(s1);
        config.setSystem(new DispatchTestSystem());

        World w = new World(config);
        w.process();
        assertEquals(1, s1.count);
    }

    public void test_Dispatch_NoListeningSystem_NoExceptions() {
        final WorldConfiguration config = new WorldConfiguration();
        final EventSystem eventManager = new EventSystem();
        config.setSystem(eventManager);
        config.setSystem(new DispatchTestSystem());
        World w = new World(config);
        w.process();
        // no exception = success
    }

    public void test_Dispatch_TwoListeningSystem_BothCalled() {

        final WorldConfiguration config = new WorldConfiguration();
        final EventSystem eventManager = new EventSystem();
        config.setSystem(eventManager);
        ReceiveTestSystem s1 = new ReceiveTestSystem();
        config.setSystem(s1);
        ReceiveTestSystem s2 = new ReceiveTestSystem();
        config.setSystem(s2);
        config.setSystem(new DispatchTestSystem());

        World w = new World(config);
        w.process();
        // no exception = success
        assertEquals(1, s1.count);
        assertEquals(1, s2.count);
    }
}
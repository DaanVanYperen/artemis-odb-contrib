package net.mostlyoriginal.gwt;

import com.artemis.World;
import com.google.gwt.junit.client.GWTTestCase;
import net.mostlyoriginal.api.event.common.EventManager;
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
        World w = new World();
        final EventManager eventManager = new EventManager();
        w.setManager(eventManager);
        ReceiveTestSystem s1 = new ReceiveTestSystem();
        w.setSystem(s1);
        w.setSystem(new DispatchTestSystem());
        w.initialize();
        w.process();
        assertEquals(1, s1.count);
    }

    public void test_Dispatch_NoListeningSystem_NoExceptions() {
        World w = new World();
        final EventManager eventManager = new EventManager();
        w.setManager(eventManager);
        w.setSystem(new DispatchTestSystem());
        w.initialize();
        w.process();
        // no exception = success
    }

    public void test_Dispatch_TwoListeningSystem_BothCalled() {
        World w = new World();
        final EventManager eventManager = new EventManager();
        w.setManager(eventManager);
        ReceiveTestSystem s1 = new ReceiveTestSystem();
        w.setSystem(s1);
        ReceiveTestSystem s2 = new ReceiveTestSystem();
        w.setSystem(s2);
        w.setSystem(new DispatchTestSystem());
        w.initialize();
        w.process();
        // no exception = success
        assertEquals(1, s1.count);
        assertEquals(1, s2.count);
    }
}
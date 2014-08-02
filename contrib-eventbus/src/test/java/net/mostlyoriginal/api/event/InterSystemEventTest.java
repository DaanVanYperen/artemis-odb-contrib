package net.mostlyoriginal.api.event;

import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Test event dispatching between systems.
 * @author Daan van Yperen
 */
public class InterSystemEventTest {

    @Test
    public void Dispatch_OneListeningSystem_SystemReceivesEvent(){
      fail("Not yet implemented");
    }

    @Test
    public void Dispatch_NoListeningSystem_NoExceptions(){
      fail("Not yet implemented");
    }

    @Test
    public void Dispatch_TwoListeningSystem_CalledBySystemOrder(){
      fail("Not yet implemented");
    }

    @Test
    public void Dispatch_TwoListeningSystemWithPriority_CalledByPriority(){
      fail("Not yet implemented");
    }

    @Test
    public void Dispatch_IdenticalListenersOnSystem_BothCalled(){
      fail("Not yet implemented");
    }

    @Test
    public void Dispatch_TwoSystemOneCancels_RemainingSystemNotCalled(){
      fail("Not yet implemented");
    }

}

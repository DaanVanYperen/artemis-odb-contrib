package net.mostlyoriginal.api.network;

import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * System responsible for auto-updating interest of
 * each player in one or more entities based on an entitystrategy.
 *
 * @author Daan van Yperen
 */
public class NetworkSubscriptionSystemTest {

    @Test
    public void IteratePlayers_NewInterestingEntity_Subscribe() {
        fail("Not yet implemented");
    }

    @Test
    public void IteratePlayers_InterestingEntityDeleted_Unsubscribe() {
        fail("Not yet implemented");
    }

    @Test
    public void IteratePlayers_RetainInterestingEntity_Nothing() {
        fail("Not yet implemented");
    }

    @Test
    public void IteratePlayers_LostInterestingEntity_Unsubscribe() {
        fail("Not yet implemented");
    }

    @Test
    public void Handle_LostPlayer_UnsubscribeEverything(){
      fail("Not yet implemented");
    }
}

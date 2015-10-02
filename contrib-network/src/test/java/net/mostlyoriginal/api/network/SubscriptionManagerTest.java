package net.mostlyoriginal.api.network;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.utils.EntityBuilder;
import net.mostlyoriginal.api.network.common.DeltaSubscriptionManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test entity subscription manager.
 *
 * @author Daan van Yperen
 */
public class SubscriptionManagerTest {

    public static final int TEST_CONNECTION_ID = 1;
    private static final Integer SECOND_TEST_CONNECTION_ID = 2;
    protected World world;
    protected DeltaSubscriptionManager subscriptionManager;
    public Entity entity;
    private Entity entity2;

    @Before
    public void setUp() throws Exception {
        subscriptionManager = new DeltaSubscriptionManager();
        world = new World(new WorldConfiguration().setSystem(subscriptionManager));
        entity = new EntityBuilder(world).build();
        entity2 = new EntityBuilder(world).build();
    }

    /**
     * Newly subscribed entity.
     */
    @Test
    public void Unsubscribe_UnsubscribeSubscribedEntity_Success() {


        subscriptionManager.subscribe(TEST_CONNECTION_ID, entity);
        subscriptionManager.unsubscribe(TEST_CONNECTION_ID, entity);

        assertEntityCount(0, entity, TEST_CONNECTION_ID);
    }

    @Test
    public void Subscribe_JustSubscribe_Success() {

        subscriptionManager.subscribe(1, entity);

        assertEntityCount(1, entity, TEST_CONNECTION_ID);
    }

    @Test
    public void Subscribe_SubscribeAlreadySubscribed_IgnoredNoException() {

        subscriptionManager.subscribe(TEST_CONNECTION_ID, entity);
        subscriptionManager.subscribe(TEST_CONNECTION_ID, entity);

        assertEntityCount(1, entity, TEST_CONNECTION_ID);
    }

    @Test
    public void Unsubscribe_JustUnsubscribe_IgnoredNoException() {

        subscriptionManager.unsubscribe(TEST_CONNECTION_ID, entity);

        assertEntityCount(0, entity, TEST_CONNECTION_ID);
    }

    @Test
    public void Unsubscribe_UnsubscribeAlreadyUnsubscribed_IgnoredNoException() {

        subscriptionManager.subscribe(1, entity);
        subscriptionManager.unsubscribe(1, entity);
        subscriptionManager.unsubscribe(1, entity);

        assertEntityCount(0, entity, TEST_CONNECTION_ID);
    }

    @Test
    public void Unsubscribe_UnsubscribeOneOfTwo_RetainOtherSubscriptions() {

        subscriptionManager.subscribe(TEST_CONNECTION_ID, entity);
        subscriptionManager.subscribe(TEST_CONNECTION_ID, entity2);
        subscriptionManager.unsubscribe(TEST_CONNECTION_ID, entity);

        assertEntityCount(0, entity, TEST_CONNECTION_ID);
        assertEntityCount(1, entity2, TEST_CONNECTION_ID);
    }

    @Test
    public void UnsubscribeFromAll_EntityWithTwoObservers_WipeFromAllObservers() {

        subscriptionManager.subscribe(TEST_CONNECTION_ID, entity);
        subscriptionManager.subscribe(SECOND_TEST_CONNECTION_ID, entity);

        subscriptionManager.unsubscribeFromAll(entity);

        assertEntityCount(0, entity, TEST_CONNECTION_ID);
        assertEntityCount(0, entity, SECOND_TEST_CONNECTION_ID);
    }

    @Test
    public void UnsubscribeFromAll_TwoEntities_UnaffectOneEntity() {

        // only one of the two entities should be touched by this.
        subscriptionManager.subscribe(TEST_CONNECTION_ID, entity);
        subscriptionManager.subscribe(SECOND_TEST_CONNECTION_ID, entity2);

        subscriptionManager.unsubscribeFromAll(entity);

        assertEntityCount(0, entity, TEST_CONNECTION_ID);
        assertEntityCount(1, entity2, SECOND_TEST_CONNECTION_ID);
    }

    /** Assert the entitycount for a certain entity/connectionID combo. */
    private void assertEntityCount(int expected, Entity entity, int connectionId) {

        int matchingSubscribers=0;
        for (Integer integer : subscriptionManager.getSubscribersOf(entity)) {
            if ( integer.equals(connectionId)) {
                matchingSubscribers++;
            }
        }

        assertEquals(expected, matchingSubscribers);

        int matchingEntities=
                subscriptionManager.getEntitiesOf(connectionId).contains(entity.getId()) ? 1 : 0;

        assertEquals(expected, matchingEntities);
    }

}

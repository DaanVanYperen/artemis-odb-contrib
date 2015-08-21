package net.mostlyoriginal.api.network.common;

import com.artemis.Entity;
import com.artemis.Manager;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.artemis.utils.IntBag;

import java.util.HashMap;
import java.util.Map;


/**
 * Tracks subscribed entities for T.
 *
 * @param <T> Type of subscriber.
 * @author Daan van Yperen
 */
public class SubscriptionManager<T> extends Manager {

    private final Bag<Bag<T>> entitySubscribers;
    private final Map<T, IntBag> subscriberEntities;

    public SubscriptionManager() {
        subscriberEntities = new HashMap<>();
        entitySubscribers = new Bag<>();
    }

    /**
     * Subscribe T to entity.
     * <p/>
     * Does nothing if already subscribed.
     *
     * @param subscriber subscriber
     * @param entity   entity to subscribe.
     */
    public void subscribe(T subscriber, Entity entity) {
        subscribe(subscriber, entity.getId());
    }

    /**
     * Subscribe T to entity.
     * <p/>
     * Does nothing if already subscribed.
     *
     * @param subscriber subscriber
     * @param entityId   entity to subscribe.
     */
    public void subscribe(T subscriber, int entityId) {

        // hook subscriber to entity.
        IntBag entities = subscriberEntities.get(subscriber);
        if (entities == null) {
            entities = new IntBag();
            subscriberEntities.put(subscriber, entities);
        }
        if (!entities.contains(entityId)) {
            entities.add(entityId);
        }

        // hook entity to subscriber.
        Bag<T> subscribers = entitySubscribers.get(entityId);
        if (subscribers == null) {
            subscribers = new Bag<>();
            entitySubscribers.set(entityId, subscribers);
        }
        if (!subscribers.contains(subscriber)) {
            subscribers.add(subscriber);
        }
    }

    /**
     * Unsubscribe T from entity.
     *
     * @param subscriber subscriber
     * @param entityId   entity to subscribe
     */
    public void unsubscribe(T subscriber, int entityId) {

        // unhook entity from subscriber
        IntBag entities = subscriberEntities.get(subscriber);
        if (entities != null) {
            int index = entities.indexOf(entityId);
            if ( index != -1 ) {
                entities.remove(index);
            }
        }

        // unhook subscriber from entity.
        Bag<T> subscribers = entitySubscribers.get(entityId);
        if (subscribers != null) {
            subscribers.remove(subscriber);
        }
    }

    /**
     * Unsubscribe T from entity.
     *
     * @param subscriber subscriber
     * @param entity     entity to subscribe
     */
    public void unsubscribe(T subscriber, Entity entity) {
        unsubscribe(subscriber,entity.getId());
    }

    /**
     * Unsubscribe e from all subscribers.
     *
     * @param entityId entity to unsubscribe
     */
    public void unsubscribeFromAll(int entityId) {
        for (T subscriber : subscriberEntities.keySet()) {
            unsubscribe(subscriber, entityId);
        }
    }

    /**
     * Unsubscribe e from all subscribers.
     *
     * @param entity entity to unsubscribe
     */
    public void unsubscribeFromAll(Entity entity) {
        final int id = entity.getId();
        for (T subscriber : subscriberEntities.keySet()) {
            unsubscribe(subscriber, id);
        }
    }


    /**
     * Get all subscribed entities of a subscriber.
     *
     * @param subscriber subscriber
     * @return all subscribed entities of a subscriber.
     */
    public IntBag getEntitiesOf(T subscriber) {
        IntBag entities = subscriberEntities.get(subscriber);
        if (entities == null) {
            entities = new IntBag();
        }
        return entities;
    }

    /**
     * Get all subscribers of an entity.
     *
     * @param entityId subscribed entity.
     * @return all subscribers of an entity.
     */
    public ImmutableBag<T> getSubscribersOf(int entityId) {
        Bag<T> subscribers = entitySubscribers.get(entityId);
        if (subscribers == null) {
            subscribers = new Bag<>();
        }
        return subscribers;
    }

    /**
     * Get all subscribers of an entity.
     *
     * @param entity subscribed entity.
     * @return all subscribers of an entity.
     */
    public ImmutableBag<T> getSubscribersOf(Entity entity) {
        return getSubscribersOf(entity.getId());
    }

    /**
     * Unsubscribe deleted entities from manager.
     */
    @Override
    public void deleted(int entityId) {
        unsubscribeFromAll(entityId);
    }

}

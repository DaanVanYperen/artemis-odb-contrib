package net.mostlyoriginal.api.network.common;

import com.artemis.Entity;
import com.artemis.Manager;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;

import java.util.HashMap;
import java.util.Map;


/**
 * Tracks subscribed entities for T.
 *
 * @param <T> Type of subscriber.
 * @author Daan van Yperen
 */
public class SubscriptionManager<T> extends Manager {

    private final Map<Entity, Bag<T>> entitySubscribers;
    private final Map<T, Bag<Entity>> subscriberEntities;

    public SubscriptionManager() {
        subscriberEntities = new HashMap<>();
        entitySubscribers = new HashMap<>();
    }

    /**
     * Subscribe T to entity.
     * <p/>
     * Does nothing if already subscribed.
     *
     * @param subscriber subscriber
     * @param e          entity to subscribe.
     */
    public void subscribe(T subscriber, Entity e) {

        // hook subscriber to entity.
        Bag<Entity> entities = subscriberEntities.get(subscriber);
        if (entities == null) {
            entities = new Bag<>();
            subscriberEntities.put(subscriber, entities);
        }
        if (!entities.contains(e)) {
            entities.add(e);
        }

        // hook entity to subscriber.
        Bag<T> subscribers = entitySubscribers.get(e);
        if (subscribers == null) {
            subscribers = new Bag<>();
            entitySubscribers.put(e, subscribers);
        }
        if (!subscribers.contains(subscriber)) {
            subscribers.add(subscriber);
        }
    }

    /**
     * Unsubscribe T from entity.
     *
     * @param subscriber subscriber
     * @param e          entity to subscribe
     */
    public void unsubscribe(T subscriber, Entity e) {

        // unhook entity from subscriber
        Bag<Entity> entities = subscriberEntities.get(subscriber);
        if (entities != null) {
            entities.remove(e);
        }

        // unhook subscriber from entity.
        Bag<T> subscribers = entitySubscribers.get(e);
        if (subscribers != null) {
            subscribers.remove(subscriber);
        }
    }

    /**
     * Unsubscribe e from all subscribers.
     *
     * @param e entity to unsubscribe
     */
    public void unsubscribeFromAll(Entity e) {
        for (T subscriber : subscriberEntities.keySet()) {
            unsubscribe(subscriber, e);
        }
    }

    /**
     * Get all subscribed entities of a subscriber.
     *
     * @param subscriber subscriber
     * @return all subscribed entities of a subscriber.
     */
    public ImmutableBag<Entity> getEntitiesOf(T subscriber) {
        Bag<Entity> entities = subscriberEntities.get(subscriber);
        if (entities == null) {
            entities = new Bag<>();
        }
        return entities;
    }

    /**
     * Get all subscribers of an entity.
     *
     * @param e subscribed entity.
     * @return all subscribers of an entity.
     */
    public ImmutableBag<T> getSubscribersOf(Entity e) {
        Bag<T> subscribers = entitySubscribers.get(e);
        if (subscribers == null) {
            subscribers = new Bag<>();
        }
        return subscribers;
    }

    /**
     * Unsubscribe deleted entities from manager.
     */
    @Override
    public void deleted(Entity e) {
        unsubscribeFromAll(e);
    }

}

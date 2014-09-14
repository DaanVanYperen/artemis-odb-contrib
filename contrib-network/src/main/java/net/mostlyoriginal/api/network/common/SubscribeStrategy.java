package net.mostlyoriginal.api.network.common;

import com.artemis.Entity;

/**
 * Entity subscription strategy.
 *
 * @author Daan van Yperen
 */
public interface SubscribeStrategy<T> {

    /**
     * Check if entity is in scope to be observed by the observer.
     * <p/>
     * Typical criteria are distance to observer.
     *
     * @param observer observer of entities.
     * @param e        observable entity.
     * @return <code>true</code> if within scope, <code>false</code> if it is out of scope.
     */
    boolean inScope(T observer, Entity e);
}
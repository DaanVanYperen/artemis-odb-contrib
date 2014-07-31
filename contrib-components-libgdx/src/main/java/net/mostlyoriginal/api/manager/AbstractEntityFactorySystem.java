package net.mostlyoriginal.api.manager;

import com.artemis.Entity;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.maps.MapProperties;

/**
 * Extend this system for entity instancing.
 *
 * @author Daan van Yperen
 */
public abstract class AbstractEntityFactorySystem extends VoidEntitySystem {

    /**
     * Instance entity archetype.
     *
     * @param entity entity archetype to instance.
     * @param cx x spawn location
     * @param cy y spawn location
     * @param properties map of map properties.
     * @return main instanced entity.
     */
    public abstract Entity createEntity(String entity, int cx, int cy, MapProperties properties);

    @Override
    protected void processSystem() {
    }
}

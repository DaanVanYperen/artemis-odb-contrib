package net.mostlyoriginal.api.component.map;

import com.artemis.Component;

import java.io.Serializable;

/**
 * Entity collides with world (map).
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.system.map.MapCollisionSystem
 */
public class MapSolid extends Component implements Serializable {
    public MapSolid() {
    }
}

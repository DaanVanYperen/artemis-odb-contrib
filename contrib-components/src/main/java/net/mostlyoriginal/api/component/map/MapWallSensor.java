package net.mostlyoriginal.api.component.map;

import com.artemis.Component;

import java.io.Serializable;

/**
 * Entity detects nearby map surfaces.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.system.map.MapWallSensorSystem
 */
public class MapWallSensor extends Component implements Serializable {
    public boolean onFloor = false;
    public boolean onHorizontalSurface = false;
    public boolean onVerticalSurface = false;

    public float wallAngle;

    public boolean onAnySurface() {
        return onHorizontalSurface || onVerticalSurface;
    }
}

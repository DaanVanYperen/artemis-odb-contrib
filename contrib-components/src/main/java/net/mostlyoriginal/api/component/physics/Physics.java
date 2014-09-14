package net.mostlyoriginal.api.component.physics;

import com.artemis.Component;

import java.io.Serializable;

/**
 * Entity has fudged physics.
 * Basic acceleration and some fudge friction on top.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.system.physics.PhysicsSystem
 */
public class Physics extends Component implements Serializable {

    // velocityX in units per second.
    public float vx;
    // velocityY in units per second.
    public float vy;
    // rotational velocity in degrees per second.
    public float vr;

    // Fudge friction!
    public float friction = 4f;

    // does nothing!
    public float bounce = 0f;

    // Maximum travel distance in units (typically pixels) per second.
    public float maxVelocity = Float.MAX_VALUE;
}

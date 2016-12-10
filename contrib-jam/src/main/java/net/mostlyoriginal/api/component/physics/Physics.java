package net.mostlyoriginal.api.component.physics;

import com.artemis.Component;
import com.artemis.annotations.Fluid;

import java.io.Serializable;

/**
 * Entity has fudged physics.
 * Basic acceleration and some fudge friction on top.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.system.physics.PhysicsSystem
 */
@Fluid(swallowGettersWithParameters = true)
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

    public Physics() {
    }

    public Physics velocity(float vx, float vy, float friction) {
        this.vx = vx;
        this.vy = vy;
        this.friction = friction;
        return this;
    }
}

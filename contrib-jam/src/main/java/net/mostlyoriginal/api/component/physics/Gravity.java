package net.mostlyoriginal.api.component.physics;

import com.artemis.Component;

import java.io.Serializable;

/**
 * Apply gravity to entity.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.system.physics.GravitySystem
 */
public class Gravity extends Component implements Serializable {

    public static final float DEFAULT_Y_GRAVITY = -9.8f;

    // x axis gravity
    public float x = 0;
    // y axis gravity
    public float y = DEFAULT_Y_GRAVITY;

    public Gravity() {}

    public Gravity(float y) {
        this.y = y;
    }

    public void set(float x, float y) { this.x=x; this.y=y;}
}

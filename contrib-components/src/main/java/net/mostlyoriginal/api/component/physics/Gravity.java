package net.mostlyoriginal.api.component.physics;

import com.artemis.Component;
import net.mostlyoriginal.api.Depends;
import net.mostlyoriginal.api.component.basic.Pos;

import java.io.Serializable;

/**
 * Apply gravity to entity.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.system.physics.GravitySystem
 */
@Depends({Physics.class, Pos.class})
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
}

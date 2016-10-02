package net.mostlyoriginal.api.component.basic;

import com.artemis.Component;
import com.badlogic.gdx.math.Interpolation;
import net.mostlyoriginal.api.component.common.ExtendedComponent;
import net.mostlyoriginal.api.component.common.Tweenable;

import java.io.Serializable;

/**
 * Rotation around origin (!)
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.system.render.AnimRenderSystem
 */
public class Angle extends ExtendedComponent<Angle> implements Tweenable<Angle> {

    public static final int ORIGIN_AUTO = Integer.MIN_VALUE;
    public static final Angle NONE = new Angle();

    /** Rotation of animation. Rotates nothing else! We're cheap. */
    public float rotation = 0;

    /** Rotational X origin */
    @Deprecated
    public int ox = ORIGIN_AUTO; // rotational origin X
    /** Rotational Y origin */
    @Deprecated
    public int oy = ORIGIN_AUTO; // rotational origin Y

    public Angle(float rotation) {
        this.rotation = rotation;
    }

    public Angle(float rotation, int ox, int oy) {
        this.rotation = rotation;
        this.ox = ox;
        this.oy = oy;
    }

    public Angle() {
    }

    @Override
    protected void reset() {
        rotation=0;
    }

    public void rotate(float angle) { this.rotation += angle; }

    @Override
    public void set(Angle angle) {
        this.rotation = angle.rotation;
    }

    @Override
    public void tween(Angle a, Angle b, float value) {
        rotation = Interpolation.linear.apply(a.rotation, b.rotation, value);
    }
}

package net.mostlyoriginal.api.component.basic;

import com.artemis.Component;

import java.io.Serializable;

/**
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.system.render.AnimRenderSystem
 * @todo turn into Matrix?
 */
public class Angle extends Component implements Serializable {
    public static final int ORIGIN_AUTO = Integer.MIN_VALUE;
    public static final Angle NONE = new Angle();

    /** Rotation of animation. Rotates nothing else! We're cheap. */
    public float rotation = 0;

    /** Rotational X origin */
    public int ox = ORIGIN_AUTO; // rotational origin X
    /** Rotational Y origin */
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
}

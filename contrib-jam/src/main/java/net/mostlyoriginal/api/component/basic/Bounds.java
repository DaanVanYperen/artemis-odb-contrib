package net.mostlyoriginal.api.component.basic;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.Serializable;

/**
 * Entity has physical bounds.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.system.physics.PhysicsSystem
 */
public class Bounds extends Component implements Serializable {

    public static final Bounds NONE = new Bounds(0,0,0,0);

    public float minx;
    public float miny;
    public float maxx;
    public float maxy;

    public Bounds(final float width, final float height) {
        this.minx =this.miny =0;
        this.maxx =width;
        this.maxy =height;
    }

    public Bounds(final float minx, final float miny, final float maxx, final float maxy) {
        this.minx = minx;
        this.miny = miny;
        this.maxx = maxx;
        this.maxy = maxy;
    }

    public Bounds(TextureRegion region) {
        this.minx = this.maxx =0;
        this.maxx = region.getRegionWidth();
        this.maxy = region.getRegionHeight();
    }

    public Bounds() {
    }

    public void set(final float minx, final float miny, final float maxx, final float maxy) {
        this.minx = minx;
        this.miny = miny;
        this.maxx = maxx;
        this.maxy = maxy;
    }

    /** Center X */
    public float cx() { return minx + (maxx - minx)/2; }
    /** Center Y */
    public float cy() { return miny + (maxy - miny)/2; }
}

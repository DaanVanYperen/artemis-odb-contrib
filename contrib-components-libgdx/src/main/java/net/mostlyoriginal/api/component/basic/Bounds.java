package net.mostlyoriginal.api.component.basic;

import com.artemis.Component;

import java.io.Serializable;

/**
 * Entity has physical bounds.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.system.physics.PhysicsSystem
 */
public class Bounds extends Component implements Serializable {

    public static final Bounds NONE = new Bounds(0,0,0,0);

    public int minx;
    public int miny;
    public int maxx;
    public int maxy;

    public Bounds(final int width, final int height) {
        this.minx =this.miny =0;
        this.maxx =width;
        this.maxy =height;
    }

    public Bounds(final int minx, final int miny, final int maxx, final int maxy) {
        this.minx = minx;
        this.miny = miny;
        this.maxx = maxx;
        this.maxy = maxy;
    }

    /* @todo port this to libgdx helper.
    public Bounds(TextureRegion region) {
        this.minx = this.maxx =0;
        this.maxx = region.getRegionWidth();
        this.maxy = region.getRegionHeight();
    }*/

    public Bounds() {
    }

    /** Center X */
    public int cx() { return minx + (maxx - minx)/2; }
    /** Center Y */
    public int cy() { return miny + (maxy - miny)/2; }
}

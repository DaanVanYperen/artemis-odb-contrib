package net.mostlyoriginal.api.component.basic;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.mostlyoriginal.api.component.common.ExtendedComponent;

import java.io.Serializable;

/**
 * Entity has physical bounds.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.system.physics.PhysicsSystem
 */
public class Bounds extends ExtendedComponent<Bounds> implements Serializable {

    public static final Bounds NONE = new Bounds(0,0,0,0);

    public float minx;
    public float miny;
    public float maxx;
    public float maxy;

    public Bounds() {
    }

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

    @Override
    protected void reset() {
        this.minx = 0;
        this.miny = 0;
        this.maxx = 0;
        this.maxy = 0;
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

    @Override
    public Bounds set(Bounds bounds) {
        set(bounds.minx, bounds.miny, bounds.maxx, bounds.maxy);
        return this;
    }
}

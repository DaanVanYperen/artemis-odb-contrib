package net.mostlyoriginal.api.component.basic;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import net.mostlyoriginal.api.component.common.ExtendedComponent;
import net.mostlyoriginal.api.component.common.Tweenable;

/**
 * World anchor for both 2d and 3d coordinate systems.
 *
 * By default Pos is the bottom-left point. {@Origin} to override.
 * Use bounds to determine the center.
 *
 * @author Daan van Yperen
 * @see Origin to override anchor point.
 * @see Bounds to provide a bounding box.
 */
public class Pos extends ExtendedComponent<Pos> implements Tweenable<Pos> {
    public Vector3 xy = new Vector3();

    public Pos() {}
    public Pos(float x, float y) {
        set(x, y);
    }
    public Pos(float x, float y, float z) {
        set(x, y, z);
    }

    @Override
    protected void reset() {
        xy.x=0;
        xy.y=0;
        xy.z=0;
    }

    @Override
    public void set(Pos pos) {
        xy.x=pos.xy.x;
        xy.y=pos.xy.y;
        xy.z=pos.xy.z;
    }

    public void set(float x, float y)
    {
        xy.x = x;
        xy.y = y;
    }

    public void set(float x, float y, float z)
    {
        xy.x = x;
        xy.y = y;
        xy.z = z;
    }

    public void set(Vector2 v)
    {
        xy.x = v.x;
        xy.y = v.y;
    }

    public void set(Vector3 v)
    {
        xy.x = v.x;
        xy.y = v.y;
        xy.z = v.z;
    }

    public float getX()
    {
        return xy.x;
    }
    public float getY()
    {
        return xy.y;
    }
    public float getZ()
    {
        return xy.z;
    }

    public void setX( float x ) { xy.x = x; }
    public void setY( float y ) { xy.y = y; }
    public void setZ( float z ) { xy.z = z; }

    @Override
    public void tween(Pos a, Pos b, float value) {
        xy.x = Interpolation.linear.apply(a.xy.x, b.xy.x, value);
        xy.y = Interpolation.linear.apply(a.xy.y, b.xy.y, value);
        xy.z = Interpolation.linear.apply(a.xy.z, b.xy.z, value);
    }
}

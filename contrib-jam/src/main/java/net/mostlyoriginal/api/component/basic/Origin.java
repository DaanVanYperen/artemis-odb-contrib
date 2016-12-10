package net.mostlyoriginal.api.component.basic;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import net.mostlyoriginal.api.component.common.ExtendedComponent;
import net.mostlyoriginal.api.component.common.Tweenable;

/**
 * Origin relative to the dimensions of this object.
 *
 * 0.5, 0.5 is center.
 *
 * @author Daan van Yperen
 */
public class Origin extends ExtendedComponent<Origin> implements Tweenable<Origin> {
    public Vector3 xy = new Vector3();

    public Origin() {}
    public Origin(float x, float y) {
        set(x, y);
    }
    public Origin(float x, float y, float z) {
        set(x, y, z);
    }

    @Override
    protected void reset() {
        xy.x=0;
        xy.y=0;
        xy.z=0;
    }

    @Override
    public void set(Origin o) {
        xy.x=o.xy.x;
        xy.y=o.xy.y;
        xy.z=o.xy.z;
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
    public void tween(Origin a, Origin b, float value) {
        xy.x = Interpolation.linear.apply(a.xy.x, b.xy.x, value);
        xy.y = Interpolation.linear.apply(a.xy.y, b.xy.y, value);
        xy.z = Interpolation.linear.apply(a.xy.z, b.xy.z, value);
    }
}

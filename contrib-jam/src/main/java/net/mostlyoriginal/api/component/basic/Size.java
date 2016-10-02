package net.mostlyoriginal.api.component.basic;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import net.mostlyoriginal.api.component.common.ExtendedComponent;
import net.mostlyoriginal.api.component.common.Tweenable;

/**
 * @author Daan van Yperen
 */
public class Size extends ExtendedComponent<Size> implements Tweenable<Size> {
    public Vector3 xy = new Vector3();

    public Size() {}
    public Size(float x, float y) {
        set(x, y);
    }
    public Size(float x, float y, float z) {
        set(x, y, z);
    }

    @Override
    protected void reset() {
        xy.x=0;
        xy.y=0;
        xy.z=0;
    }

    @Override
    public void set(Size size) {
        xy.x=size.xy.x;
        xy.y=size.xy.y;
        xy.z=size.xy.z;
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
    public void tween(Size a, Size b, float value) {
        xy.x = Interpolation.linear.apply(a.xy.x, b.xy.x, value);
        xy.y = Interpolation.linear.apply(a.xy.y, b.xy.y, value);
        xy.z = Interpolation.linear.apply(a.xy.z, b.xy.z, value);
    }
}

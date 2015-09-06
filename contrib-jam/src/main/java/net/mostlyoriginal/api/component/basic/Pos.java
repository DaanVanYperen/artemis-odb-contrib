package net.mostlyoriginal.api.component.basic;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import net.mostlyoriginal.api.component.common.ExtendedComponent;
import net.mostlyoriginal.api.component.common.Tweenable;

/**
 * Position component.
 *
 * @author Daan van Yperen
 */
public class Pos extends ExtendedComponent<Pos> implements Tweenable<Pos> {
    public Vector2 xy = new Vector2();

    public Pos(float x, float y) {
        set(x, y);
    }

    public Pos() {
    }

    @Override
    protected void reset() {
        xy.set(0, 0);
    }

    @Override
    public Pos set(Pos pos) {
        xy.set(pos.xy.x,pos.xy.y);
        return this;
    }

    public Pos set(float x, float y)
    {
        xy.set(x, y);
        return this;
    }

    public Pos set(Vector2 v)
    {
        xy.set(v);
        return this;
    }

    public float getX()
    {
        return xy.x;
    }

    public float getY()
    {
        return xy.y;
    }

    @Override
    public Pos tween(Pos a, Pos b, float value) {

        final Interpolation linear = Interpolation.linear;

        xy.x = linear.apply(a.xy.x, b.xy.x, value);
        xy.y = linear.apply(a.xy.y, b.xy.y, value);

        return this;
    }
}

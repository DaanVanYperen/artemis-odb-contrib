package net.mostlyoriginal.api.component.basic;

import com.badlogic.gdx.math.Vector2;
import net.mostlyoriginal.api.component.common.ExtendedComponent;

/**
 * Position component.
 *
 * @author Daan van Yperen
 */
public class Pos extends ExtendedComponent<Pos> {
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
}

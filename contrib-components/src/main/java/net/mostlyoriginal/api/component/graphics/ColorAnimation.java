package net.mostlyoriginal.api.component.graphics;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;

/**
 * @author Daan van Yperen
 */
public class ColorAnimation extends Component {

    public Color minColor;
    public Color maxColor;
    public Interpolation tween;
    public float duration = -1;
    public Color speed = new Color(1, 1, 1, 1);
    public Color age = new Color(0, 0, 0, 0);

    /**
     *
     * @param minColor
     * @param maxColor
     * @param tween
     * @param speed
     * @param duration duration until this ends, or -1 if unending.
     */
    public ColorAnimation(Color minColor, Color maxColor, Interpolation tween, float speed, float duration) {
        this.minColor = minColor;
        this.maxColor = maxColor;
        this.tween = tween;
        this.duration = duration;
        this.speed.r = this.speed.g = this.speed.b = this.speed.a = speed;
    }
}

package net.mostlyoriginal.api.component.graphics;

import com.artemis.Component;

/**
 * @author Daan van Yperen
 */
public class ColorAnimation extends Component {

    public Color startColor;
    public Color endColor;
    public InterpolationStrategy tween;
    public float duration = -1;
    public Color speed = new Color(1, 1, 1, 1);
    public Color age = new Color(0, 0, 0, 0);

    /**
     *
     * @param startColor
     * @param endColor
     * @param tween
     * @param speed
     * @param duration duration until this ends, or -1 if unending.
     */
    public ColorAnimation(Color startColor, Color endColor, InterpolationStrategy tween, float speed, float duration) {
        this.startColor = startColor;
        this.endColor = endColor;
        this.tween = tween;
        this.duration = duration;
        this.speed.r = this.speed.g = this.speed.b = this.speed.a = speed;
    }
}

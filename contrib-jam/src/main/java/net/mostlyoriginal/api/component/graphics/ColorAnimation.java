package net.mostlyoriginal.api.component.graphics;

import com.artemis.Component;

/**
 * @author Daan van Yperen
 */
public class ColorAnimation extends Component {

    public Tint startTint;
    public Tint endTint;
    public InterpolationStrategy tween;
    public float duration = -1;
    public Tint speed = new Tint(1, 1, 1, 1);
    public Tint age = new Tint(0, 0, 0, 0);

    /**
     *
     * @param startTint
     * @param endTint
     * @param tween
     * @param speed
     * @param duration duration until this ends, or -1 if unending.
     */
    public ColorAnimation(Tint startTint, Tint endTint, InterpolationStrategy tween, float speed, float duration) {
        this.startTint = startTint;
        this.endTint = endTint;
        this.tween = tween;
        this.duration = duration;
        this.speed.set(speed,speed,speed,speed);
    }
}

package net.mostlyoriginal.api.system.graphics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import net.mostlyoriginal.api.component.graphics.Color;
import net.mostlyoriginal.api.component.graphics.ColorAnimation;

/**
 * Tween Entity animation between two colors.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.graphics.ColorAnimation
 */
@Wire
public class ColorAnimationSystem extends EntityProcessingSystem {

    protected ComponentMapper<Color> mColor;
    protected ComponentMapper<ColorAnimation> cm;

	@SuppressWarnings("unchecked")
    public ColorAnimationSystem() {
        super(Aspect.all(Color.class, ColorAnimation.class));
    }

    @Override
    protected void process(final Entity entity) {

        final ColorAnimation colorAnimation = cm.get(entity);
        final Color color = mColor.get(entity);

        // age colors individually.
        colorAnimation.age.r += colorAnimation.speed.r * world.delta;
        colorAnimation.age.g += colorAnimation.speed.g * world.delta;
        colorAnimation.age.b += colorAnimation.speed.b * world.delta;
        colorAnimation.age.a += colorAnimation.speed.a * world.delta;

        // tween colors individually.
        color.r = colorAnimation.tween.apply( colorAnimation.startColor.r, colorAnimation.endColor.r, 1- Math.abs(colorAnimation.age.r % 2f - 1));
        color.g = colorAnimation.tween.apply( colorAnimation.startColor.g, colorAnimation.endColor.g, 1- Math.abs(colorAnimation.age.g % 2f - 1));
        color.b = colorAnimation.tween.apply( colorAnimation.startColor.b, colorAnimation.endColor.b, 1- Math.abs(colorAnimation.age.b % 2f - 1));
        color.a = colorAnimation.tween.apply( colorAnimation.startColor.a, colorAnimation.endColor.a, 1- Math.abs(colorAnimation.age.a % 2f - 1));

        if ( colorAnimation.duration != -1 )
        {
            colorAnimation.duration -= world.delta;
            if ( colorAnimation.duration <= 0 )
            {
                // always end on the end color.
                color.set(colorAnimation.endColor);
                entity.edit().remove(ColorAnimation.class);
            }

        }
    }
}

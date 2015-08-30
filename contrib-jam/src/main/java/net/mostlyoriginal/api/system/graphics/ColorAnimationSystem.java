package net.mostlyoriginal.api.system.graphics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import net.mostlyoriginal.api.component.graphics.ColorAnimation;
import net.mostlyoriginal.api.component.graphics.Tint;

/**
 * Tween Entity animation between two colors.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.graphics.ColorAnimation
 */
@Wire
public class ColorAnimationSystem extends EntityProcessingSystem {

    protected ComponentMapper<Tint> mColor;
    protected ComponentMapper<ColorAnimation> cm;

	@SuppressWarnings("unchecked")
    public ColorAnimationSystem() {
        super(Aspect.all(Tint.class, ColorAnimation.class));
    }

    @Override
    protected void process(final Entity entity) {

        final ColorAnimation colorAnimation = cm.get(entity);
        final Tint tint = mColor.get(entity);

        // age colors individually.

        colorAnimation.age.color.r += colorAnimation.speed.color.r * world.delta;
        colorAnimation.age.color.g += colorAnimation.speed.color.g * world.delta;
        colorAnimation.age.color.b += colorAnimation.speed.color.b * world.delta;
        colorAnimation.age.color.a += colorAnimation.speed.color.a * world.delta;

        // tween colors individually.
        tint.color.r = colorAnimation.tween.apply( colorAnimation.startTint.color.r, colorAnimation.endTint.color.r, 1- Math.abs(colorAnimation.age.color.r % 2f - 1));
        tint.color.g = colorAnimation.tween.apply( colorAnimation.startTint.color.g, colorAnimation.endTint.color.g, 1- Math.abs(colorAnimation.age.color.g % 2f - 1));
        tint.color.b = colorAnimation.tween.apply( colorAnimation.startTint.color.b, colorAnimation.endTint.color.b, 1- Math.abs(colorAnimation.age.color.b % 2f - 1));
        tint.color.a = colorAnimation.tween.apply( colorAnimation.startTint.color.a, colorAnimation.endTint.color.a, 1- Math.abs(colorAnimation.age.color.a % 2f - 1));

        if ( colorAnimation.duration != -1 )
        {
            colorAnimation.duration -= world.delta;
            if ( colorAnimation.duration <= 0 )
            {
                // always end on the end tint.
                tint.set(colorAnimation.endTint);
                entity.edit().remove(ColorAnimation.class);
            }

        }
    }
}

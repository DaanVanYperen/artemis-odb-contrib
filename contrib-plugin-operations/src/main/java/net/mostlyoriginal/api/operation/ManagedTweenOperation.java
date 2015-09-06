package net.mostlyoriginal.api.operation;

import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.ReflectionException;
import com.badlogic.gdx.math.Interpolation;
import net.mostlyoriginal.api.component.common.Tweenable;
import net.mostlyoriginal.api.operation.act.AbstractTweenOperation;
import net.mostlyoriginal.api.utils.Preconditions;

/**
 * Tween between two managed component states.
 *
 * From/to states are owned by this class, safe from garbage collection.
 *
 * @author Daan van Yperen
 */
public class ManagedTweenOperation<T extends Tweenable> extends AbstractTweenOperation {

	public ManagedTweenOperation(Class<T> type) {
		try {
			a = ClassReflection.newInstance(type);
			b = ClassReflection.newInstance(type);
		} catch (ReflectionException e) {
			String error = "Couldn't instantiate object of type " + type.getName();
			throw new RuntimeException(error, e);
		}
	}

	public ManagedTweenOperation<T> setup(Interpolation interpolation, float duration )
	{
		Preconditions.checkArgument(duration != 0, "Duration cannot be zero.");
		this.interpolation = Preconditions.checkNotNull(interpolation);
		this.duration = duration;
		return this;
	}

	@SuppressWarnings("unchecked")
	public final T getFrom() {
		return (T)a;
	}

	@SuppressWarnings("unchecked")
	public final T getTo() {
		return (T)b;
	}
}

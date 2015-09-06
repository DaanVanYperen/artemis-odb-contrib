package net.mostlyoriginal.api.operation;

import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.ReflectionException;
import com.badlogic.gdx.math.Interpolation;
import net.mostlyoriginal.api.component.common.Tweenable;
import net.mostlyoriginal.api.operation.act.AbstractTweenOperation;
import net.mostlyoriginal.api.utils.Preconditions;

/**
 * @author Daan van Yperen
 */
public class ManagedTweenOperation<T extends Tweenable> extends AbstractTweenOperation {

	public ManagedTweenOperation(Class<T> c) {
		try {
			a = ClassReflection.newInstance(c);
			b = ClassReflection.newInstance(c);
		} catch (ReflectionException e) {
			throw new RuntimeException("Could not instance c.");
		}
	}

	public ManagedTweenOperation<T> setup(Interpolation interpolation, float duration )
	{
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

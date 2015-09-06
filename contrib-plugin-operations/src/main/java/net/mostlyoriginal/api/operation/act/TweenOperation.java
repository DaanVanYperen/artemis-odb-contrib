package net.mostlyoriginal.api.operation.act;

import com.artemis.Component;
import com.badlogic.gdx.math.Interpolation;
import net.mostlyoriginal.api.component.Schedule;
import net.mostlyoriginal.api.component.common.Tweenable;
import net.mostlyoriginal.api.utils.Preconditions;

/**
 * Tween between two component states.
 * <p/>
 *
 * From/to states are not pool managed, and will be garbage collected.
 *
 * For common components it is best to subclass {@see net.mostlyoriginal.api.operation.ManagedTweenOperation}
 * and manage the from/to states yourself.
 *
 * @author Daan van Yperen
 * @see Tweenable
 * @see Schedule
 */
public final class TweenOperation extends AbstractTweenOperation {

	/**
	 * Setup tween between two component states.
	 *
	 * From/to states are not pool managed, and will be garbage collected.
	 *
	 * @param a             component a starting state.
	 * @param b             component b starting state.
	 * @param duration      duration of tween, in seconds.
	 * @param interpolation method of interpolation.
	 */
	public <T extends Component & Tweenable<T>> void setup(T a, T b, Interpolation interpolation, float duration) {

		final Class<?> typeA = a.getClass();
		final Class<?> typeB = b.getClass();

		if (typeA != typeB) {
			throw new IllegalArgumentException("Can't tween between different types " + typeA + " and " + typeB + ".");
		}

 		Preconditions.checkArgument(duration != 0, "Duration cannot be zero.");

		this.a = Preconditions.checkNotNull(a);
		this.b = Preconditions.checkNotNull(b);

		this.interpolation = Preconditions.checkNotNull(interpolation);
		this.duration = duration;
	}

	@Override
	public void reset() {
		super.reset();
		a = null;
		b = null;
		m = null;
	}
}

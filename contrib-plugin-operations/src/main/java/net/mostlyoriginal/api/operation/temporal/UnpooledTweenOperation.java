package net.mostlyoriginal.api.operation.temporal;

import com.artemis.Component;
import com.artemis.Entity;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import net.mostlyoriginal.api.component.Schedule;
import net.mostlyoriginal.api.component.common.Tweenable;
import net.mostlyoriginal.api.operation.common.TemporalOperation;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.mostlyoriginal.api.utils.Preconditions;

/**
 * Tween between two component states.
 * <p/>
 * <p/>
 * From/to states are not pool managed, and will be garbage collected.
 * <p/>
 * For common components it is best to subclass {@see net.mostlyoriginal.api.operation.temporal.TweenOperation}
 * and manage the from/to states yourself.
 *
 * @author Daan van Yperen
 * @see Tweenable
 * @see Schedule
 */
public final class UnpooledTweenOperation extends TemporalOperation {

	protected Component a, b;

	@Override
	public void act(float percentage, Entity e) {
		applyTween(e, percentage);
	}

	@SuppressWarnings("unchecked")
	protected void applyTween(Entity e, float a) {

		M m = M.getFor(((Component) this.a).getClass(), e.getWorld());

		// apply tween to component, create if missing.
		((Tweenable) m.create(e))
				.tween(this.a, b, MathUtils.clamp(a, 0, 1));
	}

	/**
	 * Setup tween between two component states.
	 * <p/>
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
	}
}

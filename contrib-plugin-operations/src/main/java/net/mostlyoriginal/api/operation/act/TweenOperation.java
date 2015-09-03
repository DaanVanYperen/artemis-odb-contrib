package net.mostlyoriginal.api.operation.act;

import com.artemis.Component;
import com.artemis.Entity;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import net.mostlyoriginal.api.component.Schedule;
import net.mostlyoriginal.api.component.common.Tweenable;
import net.mostlyoriginal.api.operation.common.Operation;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.mostlyoriginal.api.utils.Preconditions;

/**
 * Tween between two component states.
 *
 * @author Daan van Yperen
 * @see Tweenable
 * @see Schedule
 */
public class TweenOperation extends Operation {

	protected Tweenable a;
	protected Tweenable b;
	protected Interpolation interpolation;
	protected M m;

	protected float duration;
	protected float runtime;

	/**
	 * Setup tween between two component states.
	 *
	 * @todo lifecycle management of components.
	 * @param a component a starting state. Tweening does not release pooled components after use.
	 * @param b component b starting state. Tweening does not release pooled components after use.
	 * @param duration duration of tween, in seconds.
	 * @param interpolation method of interpolation.
	 */
	public <T extends Component & Tweenable<T>> void setup(T a, T b, Interpolation interpolation, float duration) {

		final Class<?> typeA = a.getClass();
		final Class<?> typeB = b.getClass();

		if ( typeA != typeB ) {
			throw new IllegalArgumentException("Can't tween between different types " + typeA + " and " + typeB + ".");
		}

		Preconditions.checkArgument(duration != 0, "Duration cannot be zero.");

		this.a = Preconditions.checkNotNull(a);
		this.b = Preconditions.checkNotNull(b);

		this.interpolation = Preconditions.checkNotNull(interpolation);
		this.duration = duration;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean process(float delta, Entity e) {
		runtime += delta;

		if (m == null) {
			m = new M(a.getClass(), e.getWorld());
			System.out.println("Allocated new mapper, prob want to pool this.");
		}

		float tween = interpolation.apply(runtime / duration);
		applyTween(e, tween);

		return runtime > duration;
	}

	@SuppressWarnings("unchecked")
	protected final void applyTween(Entity e, float tween) {

		// apply tween to component, create if missing.
		((Tweenable) m.create(e))
				.tween((Component) a, (Component) b, MathUtils.clamp(tween, 0, 1));
	}

	@Override
	public void reset() {
		a = null;
		b = null;
		m = null;
		interpolation = null;
		duration = 0;
		runtime = 0;
	}
}

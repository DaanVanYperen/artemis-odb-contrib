package net.mostlyoriginal.api.operation.act;

import com.artemis.Component;
import com.artemis.Entity;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import net.mostlyoriginal.api.component.Schedule;
import net.mostlyoriginal.api.component.common.Tweenable;
import net.mostlyoriginal.api.operation.common.Operation;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;

/**
 * Tween between two component states.
 *
 * Subclass {@see net.mostlyoriginal.api.operation.ManagedTweenOperation}
 * to manage concrete types and avoid GC.
 *
 * @author Daan van Yperen
 * @see Tweenable
 * @see Schedule
 * @see TweenOperation
 */
public abstract class AbstractTweenOperation extends Operation {
	protected Tweenable a;
	protected Tweenable b;
	protected Interpolation interpolation;
	protected M m;
	protected float duration;
	protected float runtime;

	@Override
	@SuppressWarnings("unchecked")
	public boolean process(float delta, Entity e) {
		runtime += delta;

		float tween = interpolation.apply(runtime / duration);
		applyTween(e, tween);

		return runtime > duration;
	}

	@SuppressWarnings("unchecked")
	protected final void applyTween(Entity e, float tween) {

		if ( m == null ) {
			// resolve component mapper if not set yet.
			// gets cleared every reset for non managed tweens.
			m = M.getFor(((Component)a).getClass(),e.getWorld());
		}

		// apply tween to component, create if missing.
		((Tweenable) m.create(e))
				.tween((Component) a, (Component) b, MathUtils.clamp(tween, 0, 1));
	}

	@Override
	public void reset() {
		interpolation = null;
		duration = 0;
		runtime = 0;
	}
}

package net.mostlyoriginal.api.operation.act;

import com.artemis.Component;
import com.artemis.Entity;
import com.badlogic.gdx.math.MathUtils;
import net.mostlyoriginal.api.component.Schedule;
import net.mostlyoriginal.api.component.common.Tweenable;
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
public abstract class AbstractTweenOperation extends TemporalOperation {
	protected Tweenable a;
	protected Tweenable b;
	protected M m;

	@Override
	public void act(float percentage, Entity e) {
		applyTween(e, percentage);
	}

	@SuppressWarnings("unchecked")
	protected final void applyTween(Entity e, float a) {

		if ( m == null ) {
			// resolve component mapper if not set yet.
			// gets cleared every reset for non managed tweens.
			m = M.getFor(((Component) this.a).getClass(),e.getWorld());
		}

		// apply tween to component, create if missing.
		((Tweenable) m.create(e))
				.tween((Component) this.a, (Component) b, MathUtils.clamp(a, 0, 1));
	}
}

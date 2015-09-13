package net.mostlyoriginal.api.operation.basic;

import com.artemis.Component;
import com.artemis.Entity;
import net.mostlyoriginal.api.component.Schedule;
import net.mostlyoriginal.api.component.common.Mirrorable;
import net.mostlyoriginal.api.component.common.Tweenable;
import net.mostlyoriginal.api.operation.common.BasicOperation;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.mostlyoriginal.api.utils.Preconditions;

/**
 * Set component state.
 *
 * Will create component if missing.
 *
 * Target state is not pooled, and will be garbage collected.
 *
 * For performance reasons it is best to subclass
 * {@see SetOperation} to manage state.
 *
 * @author Daan van Yperen
 * @see Tweenable
 * @see Schedule
 */
public final class UnpooledMirrorOperation extends BasicOperation {

	protected Component a;

	@Override
	@SuppressWarnings("unchecked")
	public void process(Entity e) {
		final M m = M.getFor(a.getClass(),e.getWorld());
		((Mirrorable)m.create(e)).set(a);
	}

	/**
	 * Set target state.
	 *
	 * @param a             component state.
	 */
	public <T extends Component & Mirrorable<T>> void setup(T a) {
		this.a = Preconditions.checkNotNull(a);
	}

	@Override
	public void reset() {
		super.reset();
		a = null;
	}
}

package net.mostlyoriginal.api.operation.basic;

import com.artemis.Component;
import net.mostlyoriginal.api.component.Schedule;
import net.mostlyoriginal.api.component.common.Mirrorable;
import net.mostlyoriginal.api.component.common.Tweenable;
import net.mostlyoriginal.api.utils.Preconditions;

/**
 * Set component state.
 *
 * Will create component if missing.
 *
 * Target state is not pool managed, and will be garbage collected.
 *
 * For common components it is best to subclass {@see ManagedMirrorOperation}
 * and manage the state yourself.
 *
 * @author Daan van Yperen
 * @see Tweenable
 * @see Schedule
 */
public final class MirrorOperation extends AbstractMirrorOperation {

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
		m = null;
		a = null;
	}
}

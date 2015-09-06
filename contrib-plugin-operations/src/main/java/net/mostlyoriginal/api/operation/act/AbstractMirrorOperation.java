package net.mostlyoriginal.api.operation.act;

import com.artemis.Component;
import com.artemis.Entity;
import net.mostlyoriginal.api.component.Schedule;
import net.mostlyoriginal.api.component.common.Mirrorable;
import net.mostlyoriginal.api.operation.common.Operation;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;

/**
 * Set component state.
 *
 * Will create component if missing. Calls {@see Mirrorable#set} on target component.
 *
 * Subclass {@see net.mostlyoriginal.api.operation.ManagedMirrorOperation}
 * to manage concrete types and avoid GC.
 *
 * @author Daan van Yperen
 * @see Schedule
 */
public abstract class AbstractMirrorOperation extends Operation {
	protected Mirrorable a;
	protected M m;

	@Override
	@SuppressWarnings("unchecked")
	public boolean process(float delta, Entity e) {

		if ( m == null ) {
			// resolve component mapper if not set yet.
			// gets cleared every reset for non managed mirrors.
			m = M.getFor(((Component)a).getClass(),e.getWorld());
		}

		// mirror or create component.
		((Mirrorable) m.create(e)).set((Component) a);

		return true;
	}
}

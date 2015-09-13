package net.mostlyoriginal.api.operation.basic;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.ReflectionException;
import net.mostlyoriginal.api.component.common.Mirrorable;
import net.mostlyoriginal.api.operation.common.BasicOperation;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;

/**
 * Set component state.
 * <p/>
 * Creates component if missing. Calls {@see Mirrorable#set} on target component.
 * <p/>
 * State is owned by this class, safe from garbage collection.
 *
 * @author Daan van Yperen
 */
public abstract class SetOperation<T extends Component & Mirrorable> extends BasicOperation {

	protected final Component a;
	protected M m;

	public SetOperation(Class<T> type) {
		try {
			a = ClassReflection.newInstance(type);
		} catch (ReflectionException e) {
			String error = "Couldn't instantiate object of type " + type.getName();
			throw new RuntimeException(error, e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void process(Entity e) {

		if ( m == null ) {
			m = M.getFor(a.getClass(), e.getWorld());
		}

		// mirror or create component.
		((Mirrorable) m.create(e)).set(a);
	}

	@SuppressWarnings("unchecked")
	public final T get() {
		return (T) a;
	}
}

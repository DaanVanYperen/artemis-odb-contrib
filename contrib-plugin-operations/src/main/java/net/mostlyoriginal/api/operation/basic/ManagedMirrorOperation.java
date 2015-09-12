package net.mostlyoriginal.api.operation.basic;

import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.ReflectionException;
import net.mostlyoriginal.api.component.common.Mirrorable;

/**
 * Set component state.
 *
 * Creates component if missing. Calls {@see Mirrorable#set} on target component.
 *
 * State is owned by this class, safe from garbage collection.
 *
 * @author Daan van Yperen
 */
public class ManagedMirrorOperation<T extends Mirrorable> extends AbstractMirrorOperation {

	public ManagedMirrorOperation(Class<T> type) {
		try {
			a = ClassReflection.newInstance(type);
		} catch (ReflectionException e) {
			String error = "Couldn't instantiate object of type " + type.getName();
			throw new RuntimeException(error, e);
		}
	}

	@SuppressWarnings("unchecked")
	public final T get() {
		return (T)a;
	}
}

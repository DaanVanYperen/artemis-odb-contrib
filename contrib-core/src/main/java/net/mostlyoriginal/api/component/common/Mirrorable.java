package net.mostlyoriginal.api.component.common;

import com.artemis.Component;

/**
 * Support mirroring state of another component.
 *
 * @author Daan van Yperen
 */
public interface Mirrorable<T extends Component & Mirrorable> {
	/**
	 * Mirror state of passed component.
	 *
	 * @param t component to mirror
	 * @return {@code this}
	 */
	 T set(T t);
}

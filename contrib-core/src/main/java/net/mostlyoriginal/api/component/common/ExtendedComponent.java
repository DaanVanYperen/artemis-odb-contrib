package net.mostlyoriginal.api.component.common;

import com.artemis.Component;
import com.artemis.PooledComponent;

import java.io.Serializable;

/**
 * Blueprint for extended component.
 *
 * Rich components:
 * - Are pooled.
 * - Can mirror state of another component.
 *
 * @author Daan van Yperen
 */
public abstract class ExtendedComponent<T extends Component> extends PooledComponent implements Serializable {

	/**
	 * Mirror state of passed component.
	 *
	 * @param t component to mirror
	 * @return {@code this}
	 */
	public abstract T set(T t);
}

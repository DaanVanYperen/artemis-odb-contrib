package net.mostlyoriginal.api.component.common;

import com.artemis.Component;
import com.artemis.PooledComponent;

import java.io.Serializable;

/**
 * Blueprint for extended component.
 *
 * @author Daan van Yperen
 */
public abstract class ExtendedComponent<T extends Component> extends PooledComponent implements Serializable {

	public abstract T set(T t);
}

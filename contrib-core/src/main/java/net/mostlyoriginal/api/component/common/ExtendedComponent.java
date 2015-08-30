package net.mostlyoriginal.api.component.common;

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
public abstract class ExtendedComponent<T extends ExtendedComponent> extends PooledComponent
		implements Serializable, Mirrorable<T> {
}

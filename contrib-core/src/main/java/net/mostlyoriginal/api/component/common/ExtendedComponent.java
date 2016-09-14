package net.mostlyoriginal.api.component.common;

import com.artemis.PooledComponent;
import com.artemis.annotations.Fluid;

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
@Fluid(swallowGettersWithParameters = true)
public abstract class ExtendedComponent<T extends ExtendedComponent> extends PooledComponent
		implements Serializable, Mirrorable<T> {
}

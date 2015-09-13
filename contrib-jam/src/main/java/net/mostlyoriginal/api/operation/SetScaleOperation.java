package net.mostlyoriginal.api.operation;

import net.mostlyoriginal.api.component.basic.Scale;
import net.mostlyoriginal.api.operation.basic.SetOperation;

/**
 * @author Daan van Yperen
 */
public class SetScaleOperation extends SetOperation<Scale> {
	public SetScaleOperation() {
		super(Scale.class);
	}
}

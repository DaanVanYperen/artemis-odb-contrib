package net.mostlyoriginal.api.operation;

import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.graphics.Tint;
import net.mostlyoriginal.api.operation.basic.SetOperation;

/**
 * @author Daan van Yperen
 */
public class SetTintOperation extends SetOperation<Tint> {
	public SetTintOperation() {
		super(Tint.class);
	}
}

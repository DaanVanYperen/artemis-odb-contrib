package net.mostlyoriginal.api.operation;

import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.operation.basic.SetOperation;

/**
 * @author Daan van Yperen
 */
public class SetPosOperation extends SetOperation<Pos> {
	public SetPosOperation() {
		super(Pos.class);
	}
}

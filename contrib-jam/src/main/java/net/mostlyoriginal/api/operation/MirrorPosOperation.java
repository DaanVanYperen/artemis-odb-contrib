package net.mostlyoriginal.api.operation;

import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.operation.basic.MirrorOperation;

/**
 * @author Daan van Yperen
 */
public class MirrorPosOperation extends MirrorOperation<Pos> {
	public MirrorPosOperation() {
		super(Pos.class);
	}
}

package net.mostlyoriginal.api.operation;

import net.mostlyoriginal.api.component.basic.Pos;

/**
 * @author Daan van Yperen
 */
public class MirrorPosOperation extends ManagedMirrorOperation<Pos> {
	public MirrorPosOperation() {
		super(Pos.class);
	}
}

package net.mostlyoriginal.api.operation;

import net.mostlyoriginal.api.component.basic.Scale;
import net.mostlyoriginal.api.operation.basic.ManagedMirrorOperation;

/**
 * @author Daan van Yperen
 */
public class MirrorScaleOperation extends ManagedMirrorOperation<Scale> {
	public MirrorScaleOperation() {
		super(Scale.class);
	}
}

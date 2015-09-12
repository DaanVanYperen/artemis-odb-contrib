package net.mostlyoriginal.api.operation;

import net.mostlyoriginal.api.component.basic.Scale;
import net.mostlyoriginal.api.operation.basic.MirrorOperation;

/**
 * @author Daan van Yperen
 */
public class MirrorScaleOperation extends MirrorOperation<Scale> {
	public MirrorScaleOperation() {
		super(Scale.class);
	}
}

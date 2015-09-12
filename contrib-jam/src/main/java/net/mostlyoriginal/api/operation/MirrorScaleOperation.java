package net.mostlyoriginal.api.operation;

import net.mostlyoriginal.api.component.basic.Scale;

/**
 * @author Daan van Yperen
 */
public class MirrorScaleOperation extends ManagedMirrorOperation<Scale> {
	public MirrorScaleOperation() {
		super(Scale.class);
	}
}

package net.mostlyoriginal.api.operation;

import net.mostlyoriginal.api.component.basic.Scale;
import net.mostlyoriginal.api.operation.temporal.TweenOperation;

/**
 * @author Daan van Yperen
 */
public class TweenScaleOperation extends TweenOperation<Scale> {
	public TweenScaleOperation() {
		super(Scale.class);
	}
}

package net.mostlyoriginal.api.operation;

import net.mostlyoriginal.api.component.basic.Scale;
import net.mostlyoriginal.api.operation.temporal.ManagedTweenOperation;

/**
 * @author Daan van Yperen
 */
public class TweenScaleOperation extends ManagedTweenOperation<Scale> {
	public TweenScaleOperation() {
		super(Scale.class);
	}
}

package net.mostlyoriginal.api.operation;

import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.graphics.Tint;
import net.mostlyoriginal.api.operation.temporal.TweenOperation;

/**
 * Tween between tints.
 *
 * @author Daan van Yperen
 */
public class TweenTintOperation extends TweenOperation<Tint> {
	public TweenTintOperation() {
		super(Tint.class);
	}
}

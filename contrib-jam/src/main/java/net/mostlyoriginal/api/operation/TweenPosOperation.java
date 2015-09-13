package net.mostlyoriginal.api.operation;

import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.operation.temporal.TweenOperation;

/**
 * Tween between pos.
 *
 * @author Daan van Yperen
 */
public class TweenPosOperation extends TweenOperation<Pos> {
	public TweenPosOperation() {
		super(Pos.class);
	}
}

package net.mostlyoriginal.api.operation;

import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.operation.temporal.ManagedTweenOperation;

/**
 * Tween between pos.
 *
 * @author Daan van Yperen
 */
public class TweenPosOperation extends ManagedTweenOperation<Pos> {
	public TweenPosOperation() {
		super(Pos.class);
	}
}

package net.mostlyoriginal.api.operation;

import net.mostlyoriginal.api.component.basic.Pos;

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

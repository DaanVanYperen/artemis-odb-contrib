package net.mostlyoriginal.api.plugin.extendedcomponentmapper;

import net.mostlyoriginal.api.component.common.ExtendedComponent;

/**
 * @author Daan van Yperen
 */
public class Pos extends ExtendedComponent<Pos> {
	int x;
	int y;

	public Pos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Pos() {
	}

	@Override
	protected void reset() {
		x = 0;
		y = 0;
	}

	@Override
	public Pos set(Pos pos) {
		x = pos.x;
		y = pos.y;
		return this;
	}
}

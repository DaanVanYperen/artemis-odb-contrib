package net.mostlyoriginal.api.component.ui;

import net.mostlyoriginal.api.component.common.ExtendedComponent;

/**
 * @author Daan van Yperen
 */
public class Label extends ExtendedComponent<Label> {

	public String text;
	public Align align = Align.LEFT;

	public Label() {}
	public Label(String text) {
		this.text = text;
	}

	@Override
	protected void reset() {
		text = null;
		align = Align.LEFT;
	}

	@Override
	public Label set(Label label) {
		text = label.text;
		align = label.align;
		return this;
	}

	public enum Align {
		LEFT, RIGHT
	}
}

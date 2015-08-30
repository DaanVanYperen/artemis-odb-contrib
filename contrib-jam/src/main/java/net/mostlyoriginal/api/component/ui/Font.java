package net.mostlyoriginal.api.component.ui;

import net.mostlyoriginal.api.component.common.ExtendedComponent;

/**
 * @author Daan van Yperen
 */
public class Font extends ExtendedComponent<Font> {

	public String fontName;
	public float scale = 1f;

	public Font() {}
	public Font(String fontName) {
		this.fontName = fontName;
	}

	@Override
	public Font set(Font font) {
		fontName = font.fontName;
		return this;
	}

	@Override
	protected void reset() {
		fontName = null;
		scale = 1f;
	}
}

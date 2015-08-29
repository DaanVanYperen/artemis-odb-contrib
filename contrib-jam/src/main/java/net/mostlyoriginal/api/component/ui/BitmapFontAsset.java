package net.mostlyoriginal.api.component.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import net.mostlyoriginal.api.component.common.ExtendedComponent;

/**
 * @author Daan van Yperen
 */
public class BitmapFontAsset extends ExtendedComponent<BitmapFontAsset> {

	public BitmapFont bitmapFont;

	@Override
	public BitmapFontAsset set(BitmapFontAsset asset) {
		bitmapFont = asset.bitmapFont;
		return this;
	}

	@Override
	protected void reset() {
		bitmapFont = null;
	}
}

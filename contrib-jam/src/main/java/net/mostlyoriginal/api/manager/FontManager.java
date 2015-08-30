package net.mostlyoriginal.api.manager;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ObjectMap;
import net.mostlyoriginal.api.component.ui.BitmapFontAsset;
import net.mostlyoriginal.api.component.ui.Font;

/**
 * Converts {@see Font} into {@see BitmapFontAsset}.
 *
 * To update asset after changing Font, remove it and it
 * will be automatically recreated.
 *
 * @author Daan van Yperen
 */
public class FontManager extends AssetManager<Font, BitmapFontAsset> {

	public FontManager() {
		super(Font.class, BitmapFontAsset.class);
	}

	@Override
	protected void setup(Entity entity, Font font, BitmapFontAsset bitmapFontAsset) {
		if (font.fontName == null ) {
			throw new RuntimeException("FontManager: font.fontName is null.");
		}
		bitmapFontAsset.bitmapFont = getFont(font.fontName);
	}

	private final ObjectMap<String, BitmapFont> fonts = new ObjectMap<>();

	/** loads font, cache passthrough. */
	public BitmapFont getFont(String fontName) {
		BitmapFont font = this.fonts.get(fontName);
		if ( font == null )
		{
			font = loadFont(fontName);
			fonts.put(fontName, font);
		}
		return font;
	}

	private BitmapFont loadFont(final String fontName) {
		return new BitmapFont(Gdx.files.internal(fontName + ".fnt"), false);
	}

}

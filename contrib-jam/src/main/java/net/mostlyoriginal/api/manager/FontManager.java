package net.mostlyoriginal.api.manager;

import com.artemis.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * @author Daan van Yperen
 */
public class FontManager extends Manager {

	private final ObjectMap<String, BitmapFont> font = new ObjectMap<>();

	@Override
	protected void initialize() {
		super.initialize();
	}

	private BitmapFont loadFont(final String fontName) {
		return new BitmapFont(Gdx.files.internal(fontName + ".fnt"), false);
	}
}

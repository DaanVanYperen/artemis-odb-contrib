package net.mostlyoriginal.api.manager;

import com.artemis.BaseSystem;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

/**
 * Basic asset system for serving images from a single texture.
 *
 * @author Daan van Yperen
 * @deprecated implement AbstractAssetManager instead. Homebrew sound systems.
 */
@Deprecated
public class AbstractAssetSystem extends BaseSystem {
    public Texture tileset;
    public HashMap<String, Animation> sprites = new HashMap<>();

    public AbstractAssetSystem() {
        this("tiles.png");
    }

    public AbstractAssetSystem(String filename) {
        tileset = new Texture(filename);
    }

    public Animation get(final String identifier) {
        return sprites.get(identifier);
    }

    public Animation add(final String identifier, int x1, int y1, int w, int h, int repeatX) {
        return add(identifier, x1, y1, w, h, repeatX, 1, tileset);
    }

    public Animation add(final String identifier, int x1, int y1, int w, int h, int repeatX, int repeatY) {
        return add(identifier, x1, y1, w, h, repeatX, repeatY, tileset);
    }

    public Animation add(final String identifier, int x1, int y1, int w, int h, int repeatX, int repeatY, Texture texture) {

        return add(identifier, x1, y1, w, h, repeatX, repeatY, tileset, 0.5f);
    }

    public Animation add(final String identifier, int x1, int y1, int w, int h, int repeatX, int repeatY, Texture texture, float frameDuration) {

        TextureRegion[] regions = new TextureRegion[repeatX*repeatY];

        int count = 0;
        for (int y = 0; y < repeatY; y++) {
            for (int x = 0; x < repeatX; x++) {
                regions[count++] = new TextureRegion(texture, x1 + w * x, y1 + h * y, w, h);
            }
        }

        final Animation value = new Animation(frameDuration, regions);
        sprites.put(identifier, value);
        return value;
    }

    @Override
    protected void processSystem() {
    }

    public void dispose() {
        sprites.clear();
        tileset.dispose();
        tileset = null;
    }
}

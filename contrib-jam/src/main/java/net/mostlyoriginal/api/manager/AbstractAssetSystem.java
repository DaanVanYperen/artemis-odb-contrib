package net.mostlyoriginal.api.manager;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import java.util.HashMap;

/**
 * Basic asset system for serving images from a single texture.
 *
 * @author Daan van Yperen
 */
public class AbstractAssetSystem extends BaseSystem {
    public Texture tileset;
    public HashMap<String, Animation> sprites = new HashMap<String, Animation>();
    public HashMap<String, Sound> sounds = new HashMap<String, Sound>();
    protected float sfxVolume = 0.2f;

    public AbstractAssetSystem() {
        this("tiles.png");
    }

    public AbstractAssetSystem(String filename) {
        tileset = new Texture(filename);
    }

    public Animation get(final String identifier) {
        return sprites.get(identifier);
    }

    public Sound getSfx(final String identifier) {
        return sounds.get(identifier);
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

    protected void loadSounds(String[] soundnames) {
        for (String identifier : soundnames) {
            sounds.put(identifier, Gdx.audio.newSound(Gdx.files.internal("sfx/" + identifier + ".mp3")));
        }
    }

    public void playSfx(String name) {
        if (sfxVolume > 0 )
        {
            Sound sfx = getSfx(name);
            sfx.stop();
            sfx.play(sfxVolume, MathUtils.random(1f, 1.04f), 0);
        }
    }

    public void dispose() {
        sprites.clear();
        tileset.dispose();
        tileset = null;
    }
}

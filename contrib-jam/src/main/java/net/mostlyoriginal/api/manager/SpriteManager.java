package net.mostlyoriginal.api.manager;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import net.mostlyoriginal.api.component.basic.Size;
import net.mostlyoriginal.api.component.graphics.Sprite;
import net.mostlyoriginal.api.component.graphics.SpriteAsset;
import net.mostlyoriginal.api.component.graphics.Terrain;
import net.mostlyoriginal.api.component.graphics.TerrainAsset;

/**
 * Sprite loader.
 *
 * @author Daan van Yperen
 */
public class SpriteManager extends AssetManager<Sprite, SpriteAsset> {

    protected ComponentMapper<Size> mSize;

    public SpriteManager() {
        super(Sprite.class, SpriteAsset.class);
    }

    private com.badlogic.gdx.assets.AssetManager manager = new com.badlogic.gdx.assets.AssetManager();

    @Override
    protected void setup(Entity e, Sprite sprite, SpriteAsset spriteAsset) {
        spriteAsset.asset = new Texture(sprite.id);

        // set size to asset.
        if (!mSize.has(e)) {
            mSize.create(e).set(spriteAsset.asset.getWidth(), spriteAsset.asset.getHeight());
        }
    }
}

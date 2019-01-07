package net.mostlyoriginal.api.manager;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import net.mostlyoriginal.api.component.basic.Size;
import net.mostlyoriginal.api.component.graphics.Terrain;
import net.mostlyoriginal.api.component.graphics.TerrainAsset;
import net.mostlyoriginal.api.component.ui.BitmapFontAsset;
import net.mostlyoriginal.api.component.ui.Font;

/**
 * @author Daan van Yperen
 */
public class TerrainManager extends AssetManager<Terrain, TerrainAsset> {

    protected ComponentMapper<Size> mSize;
    private TmxMapLoader loader;

    public TerrainManager() {
        super(Terrain.class, TerrainAsset.class);
    }

    @Override
    protected void initialize() {
        super.initialize();
        loader = new TmxMapLoader();
    }

    @Override
    protected void setup(int e, Terrain terrain, TerrainAsset asset) {
        if (terrain.id == null ) {
            throw new RuntimeException("TerrainManager: terrain.id is null.");
        }
        asset.map = loader.load(terrain.id);
        asset.renderer = new OrthogonalTiledMapRenderer(asset.map);
        //asset.layers = map.getLayers().getByType(TiledMapTileLayer.class);
        final MapProperties properties = asset.map.getProperties();
        asset.width = properties.get("width", Integer.class);
        asset.height = properties.get("height", Integer.class);
        asset.tileWidth = properties.get("tilewidth", Integer.class);
        asset.tileHeight = properties.get("tileheight", Integer.class);

        // set unitialized size to asset.
        if (!mSize.has(e)) {
            mSize.create(e).set(asset.pixelWidth(), asset.pixelHeight());
        }
    }
}

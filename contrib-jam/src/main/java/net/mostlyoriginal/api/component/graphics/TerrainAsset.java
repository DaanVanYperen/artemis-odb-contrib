package net.mostlyoriginal.api.component.graphics;

import com.artemis.PooledComponent;
import com.artemis.annotations.Fluid;
import com.artemis.annotations.Transient;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;

/**
 * @author Daan van Yperen
 */
@Transient
public class TerrainAsset extends PooledComponent {

    public TiledMap map;
    public MapRenderer renderer;
    public int width;
    public int height;
    public int tileWidth;
    public int tileHeight;

    public int pixelWidth() { return width * tileWidth; }
    public int pixelHeight() { return height * tileHeight; }

    @Override
    protected void reset() {
        renderer = null;
        map = null;
    }
}

package net.mostlyoriginal.api.system.map;

/**
 * @author Daan van Yperen
 */

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import net.mostlyoriginal.api.manager.AbstractEntityFactorySystem;
import net.mostlyoriginal.api.utils.MapMask;

/**
 * Handles tiled map loading.
 *
 * @author Daan van Yperen
 */
@Wire
public class TiledMapSystem extends BaseSystem {

    private final String mapFilename;

    private AbstractEntityFactorySystem entityFactorySystem;
    public TiledMap map;

    private int width;
    private int height;
    private int tileWidth;
    private int tileHeight;

    private boolean isSetup;

    public Array<TiledMapTileLayer> layers;

    public TiledMapSystem(String mapFilename) {
        this.mapFilename = mapFilename;
    }

    @Override
    protected void initialize() {
        map = new TmxMapLoader().load(mapFilename);
        layers = map.getLayers().getByType(TiledMapTileLayer.class);
        width = map.getProperties().get("width", Integer.class);
        height = map.getProperties().get("height", Integer.class);
        tileWidth = map.getProperties().get("tilewidth", Integer.class);
        tileHeight = map.getProperties().get("tileheight", Integer.class);
    }

    public MapMask getMask(String property) {
        return new MapMask(height, width, tileWidth, tileHeight, layers, property);
    }

    /**
     * Spawn map entities.
     */
    protected void setup() {
        for (TiledMapTileLayer layer : layers) {
            for (int ty = 0; ty < height; ty++) {
                for (int tx = 0; tx < width; tx++) {
                    final TiledMapTileLayer.Cell cell = layer.getCell(tx, ty);
                    if (cell != null) {
                        final MapProperties properties = cell.getTile().getProperties();
                        if (properties.containsKey("entity")) {
                            entityFactorySystem.createEntity((String)properties.get("entity"), tx * tileWidth, ty * tileHeight, properties);
                            layer.setCell(tx, ty, null);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void processSystem() {
        if (!isSetup) {
            isSetup = true;
            setup();
        }
    }

}

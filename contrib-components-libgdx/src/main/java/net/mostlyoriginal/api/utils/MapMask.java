package net.mostlyoriginal.api.utils;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;

/**
 * Creates a mask based on tiles with a certain propertyKey.
 *
 * Useful for creating a mask for collidable parts of the map.
 *
 * @author Daan van Yperen
 */
public class MapMask {

    public final boolean[][] v;
    public final int height;
    public final int width;
    private final int tileWidth;
    private final int tileHeight;

    public MapMask(int height, int width, int tileWidth, int tileHeight, Array<TiledMapTileLayer> layers, String propertyKey) {
        this.height = height;
        this.width = width;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        v = new boolean[height][width];
        generate(layers, propertyKey);
    }

    /**
     * @param x grid coordinates
     * @param y grid coordinates.
     * @return TRUE when property found at TILE coordinates, FALSE if otherwise or out of bounds.
     */
    public boolean atGrid( final int x, final int y, boolean outOfBoundsResult )
    {
        if ( x >= width || x < 0 || y < 0 || y >= height  ) return outOfBoundsResult;
        return v[y][x];
    }

    /**
     *
     * @param x
     * @param y
     * @return TRUE when property found at PIXEL coordinates.
     */
    public boolean atScreen( final int x, final int y, boolean outOfBoundsResult)
    {
        return atGrid((int)(x / tileWidth),(int)(y / tileHeight), outOfBoundsResult);
    }

    public boolean atScreen( final float x, final float y, boolean outOfBoundsResult)
    {
        return atGrid((int)((int)x / tileWidth),(int)((int)y / tileHeight), outOfBoundsResult);
    }

    private void generate(Array<TiledMapTileLayer> layers, String propertyKey) {
        for (TiledMapTileLayer layer : layers) {
            for (int ty = 0; ty < height; ty++) {
                for (int tx = 0; tx < width; tx++) {
                    final TiledMapTileLayer.Cell cell = layer.getCell(tx, ty);
                    if ( cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(propertyKey)) {
                        v[ty][tx] = true;
                    }
                }
            }
        }
    }
}


package net.mostlyoriginal.api.system.render;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import net.mostlyoriginal.api.system.camera.CameraSystem;
import net.mostlyoriginal.api.system.map.TiledMapSystem;

/**
 * Renders tiled map with libgdx.7
 *
 * @author Daan van Yperen
 */
@Wire
public class MapRenderSystem extends BaseSystem {

    public final String layer = "infront";
    private TiledMapSystem mapSystem;
    private CameraSystem cameraSystem;

    public IndividualLayerRenderer renderer;


    @Override
    protected void initialize() {
        renderer = new IndividualLayerRenderer(mapSystem.map);
    }

    @Override
    protected void processSystem() {
        renderer.setView(cameraSystem.camera);
        for (MapLayer layer : mapSystem.map.getLayers()) {
            if (layer.isVisible()) {
                renderer.renderLayer((TiledMapTileLayer) layer);
            }
        }
    }

    /**
     * Helper for rendering individual map layers.
     */
    public static class IndividualLayerRenderer extends OrthogonalTiledMapRenderer {
        public IndividualLayerRenderer(TiledMap map) {
            super(map);
        }

        public void renderLayer(TiledMapTileLayer layer) {
            batch.setColor(1f, 1f, 1f, 1f);
            beginRender();
            super.renderTileLayer(layer);
            endRender();
        }
    }

}

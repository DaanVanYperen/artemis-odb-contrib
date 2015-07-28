package net.mostlyoriginal.api.system.camera;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Setup and manages basic orthographic camera.
 *
 * @author Daan van Yperen
 */
public class CameraSystem extends BaseSystem {

    public OrthographicCamera camera;
    public OrthographicCamera guiCamera;
    public final float zoom;

    public CameraSystem( float width, float height )
    {
        this.zoom = 1;
        setupViewport(width,height);
    }

    /**
     * @param zoom How much
     */
    public CameraSystem( float zoom ) {
        this.zoom = zoom;
        float zoomFactorInverter = 1f/zoom;
        setupViewport(Gdx.graphics.getWidth() * zoomFactorInverter, Gdx.graphics.getHeight() * zoomFactorInverter);
    }

    protected void setupViewport( float width, float height) {
        camera = new OrthographicCamera(width, height);
        camera.setToOrtho(false, width, height);
        camera.update();

        guiCamera = new OrthographicCamera(width, height);
        guiCamera.setToOrtho(false, width, height);
        guiCamera.update();
    }

    @Override
    protected void processSystem() {

    }
}

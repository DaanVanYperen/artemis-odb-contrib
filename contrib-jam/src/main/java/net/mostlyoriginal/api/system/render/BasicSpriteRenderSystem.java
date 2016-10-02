package net.mostlyoriginal.api.system.render;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import net.mostlyoriginal.api.component.basic.Angle;
import net.mostlyoriginal.api.component.basic.Origin;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.basic.Size;
import net.mostlyoriginal.api.component.graphics.Invisible;
import net.mostlyoriginal.api.component.graphics.Render;
import net.mostlyoriginal.api.component.graphics.SpriteAsset;
import net.mostlyoriginal.api.component.graphics.TerrainAsset;
import net.mostlyoriginal.api.system.camera.CameraSystem;
import net.mostlyoriginal.api.system.delegate.DeferredEntityProcessingSystem;
import net.mostlyoriginal.api.system.delegate.EntityProcessPrincipal;

/**
 * Very basic sprite render system.
 *
 * @author Daan van Yperen
 */
@Wire
public class BasicSpriteRenderSystem extends DeferredEntityProcessingSystem {

    private CameraSystem cameraSystem;

    protected ComponentMapper<SpriteAsset> mSpriteAsset;
    protected ComponentMapper<Pos> mPos;
    protected ComponentMapper<Angle> mAngle;
    protected ComponentMapper<Origin> mOrigin;
    protected ComponentMapper<Size> mSize;

    private static final Origin DEFAULT_ORIGIN = new Origin();
    private static final Size DEFAULT_SIZE = new Size(0,0,0);
    private SpriteBatch batch = new SpriteBatch();
    private Angle angle = new Angle(0);

    public BasicSpriteRenderSystem(EntityProcessPrincipal principal) {
        super(Aspect.all(Pos.class, SpriteAsset.class, Render.class).exclude(Invisible.class), principal);
    }

    private TextureRegion region = new TextureRegion();

    @Override
    protected void process(int e) {

        Pos pos = mPos.get(e);
        batch.setProjectionMatrix(cameraSystem.camera.combined);
        batch.begin();
        Texture asset = mSpriteAsset.get(e).asset;

        final Angle angle = mAngle.getSafe(e, this.angle);

        region.setTexture(asset);
        region.setRegion(0,0,32,32);

        final Origin origin = mOrigin.getSafe(e, DEFAULT_ORIGIN);
        final Size size = mSize.getSafe(e, DEFAULT_SIZE);

        float ox = size.getX() * origin.getX();
        float oy = size.getY() * origin.getY();

        batch.draw(
                region,
                pos.xy.x - (size.getX() - ox),
                pos.xy.y - (size.getY() - oy),
                ox,
                oy,
                size.getX(),
                size.getY(),
                1f,1f,
                angle.rotation);

        batch.end();
    }

}

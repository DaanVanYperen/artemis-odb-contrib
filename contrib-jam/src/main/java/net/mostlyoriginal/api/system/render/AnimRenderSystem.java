package net.mostlyoriginal.api.system.render;

/**
 * @author Daan van Yperen
 */

import com.artemis.Aspect;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.mostlyoriginal.api.component.basic.Angle;
import net.mostlyoriginal.api.component.basic.Origin;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.basic.Scale;
import net.mostlyoriginal.api.component.graphics.*;
import net.mostlyoriginal.api.component.graphics.Animation;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
import net.mostlyoriginal.api.system.camera.CameraSystem;
import net.mostlyoriginal.api.system.delegate.DeferredEntityProcessingSystem;
import net.mostlyoriginal.api.system.delegate.EntityProcessPrincipal;

/**
 * Render animations.
 *
 * @author Daan van Yperen
 */
@Wire
public class AnimRenderSystem extends DeferredEntityProcessingSystem {

    public static final int DEFAULT_SPRITEBATCH_SIZE = 2000;

    protected M<Pos> mPos;
    protected M<Origin> mOrigin;
    protected M<AnimationAsset> mAnimationAsset;
    protected M<Animation> mAnimation;
    protected M<Tint> mTint;
    protected M<Angle> mAngle;
    protected M<Scale> mScale;

    protected CameraSystem cameraSystem;

    protected SpriteBatch batch;
    private int spriteBatchSize;

    public AnimRenderSystem(EntityProcessPrincipal principal) {
        this(principal, DEFAULT_SPRITEBATCH_SIZE);
    }

    @SuppressWarnings("unchecked")
    public AnimRenderSystem(EntityProcessPrincipal principal, int spriteBatchSize) {
        super(Aspect.all(Pos.class, Animation.class, AnimationAsset.class, Render.class).exclude(Invisible.class), principal);
        this.spriteBatchSize = spriteBatchSize;
    }

    @Override
    protected void initialize() {
        super.initialize();
        spriteBatchSize = 2000;
        batch = new SpriteBatch(spriteBatchSize);
    }

    @Override
    protected void begin() {
        batch.setProjectionMatrix(cameraSystem.camera.combined);
        batch.begin();
    }

    @Override
    protected void end() {
        batch.end();
    }

    protected void process(final int e) {

        final Animation animation = mAnimation.get(e);
        final AnimationAsset asset = mAnimationAsset.get(e);
        animation.age += world.delta;

        renderKeyframe(e, asset.asset.getKeyFrame(Math.abs(animation.age)));
    }

    private void renderKeyframe(int e, TextureRegion frame) {

        batch.setColor(mTint.getSafe(e, Tint.WHITE).color);

        final float scale = mScale.getSafe(e, Scale.DEFAULT).scale;
        float width = frame.getRegionWidth() * scale;
        float height = frame.getRegionHeight() * scale;

        final Pos pos = mPos.get(e);
        final Angle angle = mAngle.getSafe(e, Angle.NONE);
        if (angle.rotation != 0) {
            final Origin origin = mOrigin.getSafe(e, Origin.DEFAULT);

            batch.draw(frame,
                    pos.xy.x,
                    pos.xy.y,
                    width * origin.getX(),
                    height * origin.getY(),
                    width,
                    height, 1, 1,
                    angle.rotation);
        } else {
            batch.draw(frame,
                    pos.xy.x,
                    pos.xy.y,
                    width,
                    height);
        }
    }

}

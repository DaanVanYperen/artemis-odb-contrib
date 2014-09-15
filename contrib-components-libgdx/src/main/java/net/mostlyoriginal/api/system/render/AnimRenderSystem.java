package net.mostlyoriginal.api.system.render;

/**
 * @author Daan van Yperen
 */

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.mostlyoriginal.api.component.basic.Angle;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.graphics.Anim;
import net.mostlyoriginal.api.component.graphics.Color;
import net.mostlyoriginal.api.component.graphics.Renderable;
import net.mostlyoriginal.api.manager.AbstractAssetSystem;
import net.mostlyoriginal.api.system.camera.CameraSystem;
import net.mostlyoriginal.api.system.delegate.DeferredEntityProcessingSystem;
import net.mostlyoriginal.api.system.delegate.EntityProcessPrincipal;

/**
 * Render and progress animations.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.graphics.Anim
 */
@Wire
public class AnimRenderSystem extends DeferredEntityProcessingSystem {

    protected ComponentMapper<Pos> mPos;
    protected ComponentMapper<Anim> mAnim;
    protected ComponentMapper<Color> mColor;
    protected ComponentMapper<Angle> mAngle;

    protected CameraSystem cameraSystem;
    protected AbstractAssetSystem abstractAssetSystem;

    protected SpriteBatch batch;

    public AnimRenderSystem(EntityProcessPrincipal principal) {
        super(Aspect.getAspectForAll(Pos.class, Anim.class, Renderable.class), principal);
    }

    @Override
    protected void initialize() {
        super.initialize();
        batch = new SpriteBatch(2000);
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

    protected void process(final Entity entity) {

        final Anim anim = mAnim.get(entity);
        final Pos pos = mPos.get(entity);
        final Angle angle = mAngle.has(entity) ? mAngle.get(entity) : Angle.NONE;

        anim.age += world.delta * anim.speed;

        if ( mColor.has(entity) )
        {
            final Color color = mColor.get(entity);
            batch.setColor(color.r, color.g, color.b, color.a);
        } else {
            batch.setColor(1f,1f,1f,1f);
        }

        if ( anim.id != null ) drawAnimation(anim, angle, pos, anim.id);
        if ( anim.id2 != null ) drawAnimation(anim, angle, pos, anim.id2);
    }

    /** Pixel perfect aligning. */
    private float roundToPixels(final float val) {
        // since we use camera zoom rounding to integers doesn't work properly.
        return ((int)(val * cameraSystem.zoom)) / (float)cameraSystem.zoom;
    }

    private void drawAnimation(final Anim animation, final Angle angle, final Pos position, String id) {

        // don't support backwards yet.
        if ( animation.age < 0 ) return;

        final com.badlogic.gdx.graphics.g2d.Animation gdxanim = abstractAssetSystem.get(id);
        if ( gdxanim == null) return;

        final TextureRegion frame = gdxanim.getKeyFrame(animation.age, animation.loop);

        if ( animation.flippedX)
        {
            // mirror
            batch.draw(frame.getTexture(),
                    roundToPixels(position.x),
                    roundToPixels(position.y),
                    angle.ox == Angle.ORIGIN_AUTO ? frame.getRegionWidth() * animation.scale * 0.5f : angle.ox,
                    angle.oy == Angle.ORIGIN_AUTO ? frame.getRegionHeight() * animation.scale * 0.5f : angle.oy,
                    frame.getRegionWidth() * animation.scale,
                    frame.getRegionHeight() * animation.scale,
                    1f,
                    1f,
                    angle.rotation,
                    frame.getRegionX(),
                    frame.getRegionY(),
                    frame.getRegionWidth(),
                    frame.getRegionHeight(),
                    true,
                    false);

        } else if ( angle.rotation != 0 )
        {
            batch.draw(frame,
                    roundToPixels(position.x),
                    roundToPixels(position.y),
                    angle.ox == Angle.ORIGIN_AUTO ? frame.getRegionWidth() * animation.scale * 0.5f : angle.ox,
                    angle.oy == Angle.ORIGIN_AUTO ? frame.getRegionHeight() * animation.scale * 0.5f : angle.oy,
                    frame.getRegionWidth() * animation.scale,
                    frame.getRegionHeight() * animation.scale, 1, 1,
                    angle.rotation);
        } else {
            batch.draw(frame,
                    roundToPixels(position.x),
                    roundToPixels(position.y),
                    frame.getRegionWidth() * animation.scale,
                    frame.getRegionHeight() * animation.scale);
        }
    }
}

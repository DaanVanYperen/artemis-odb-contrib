package net.mostlyoriginal.api.system.render;

/**
 * @author Daan van Yperen
 */

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.mostlyoriginal.api.component.basic.Angle;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.basic.Scale;
import net.mostlyoriginal.api.component.graphics.Anim;
import net.mostlyoriginal.api.component.graphics.Invisible;
import net.mostlyoriginal.api.component.graphics.Renderable;
import net.mostlyoriginal.api.component.graphics.Tint;
import net.mostlyoriginal.api.manager.AbstractAssetSystem;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.M;
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

    protected M<Pos> mPos;
    protected M<Anim> mAnim;
    protected M<Tint> mTint;
    protected M<Angle> mAngle;
    protected M<Scale> mScale;

    protected CameraSystem cameraSystem;
    protected AbstractAssetSystem abstractAssetSystem;

    protected SpriteBatch batch;

    public AnimRenderSystem(EntityProcessPrincipal principal) {
        super(Aspect.all(Pos.class, Anim.class, Renderable.class).exclude(Invisible.class), principal);
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

        final Anim anim   = mAnim.get(entity);
        final Pos pos     = mPos.get(entity);
        final Angle angle = mAngle.getSafe(entity, Angle.NONE);
        final float scale = mScale.getSafe(entity, Scale.DEFAULT).scale;

        anim.age += world.delta * anim.speed;

        batch.setColor(mTint.getSafe(entity, Tint.WHITE).color);

        if ( anim.id != null ) drawAnimation(anim, angle, pos, anim.id,scale);
        if ( anim.id2 != null ) drawAnimation(anim, angle, pos, anim.id2,scale);
    }

    /** Pixel perfect aligning. */
    private float roundToPixels(final float val) {
        // since we use camera zoom rounding to integers doesn't work properly.
        return ((int)(val * cameraSystem.zoom)) / (float)cameraSystem.zoom;
    }

    private void drawAnimation(final Anim animation, final Angle angle, final Pos position, String id, float scale) {

        // don't support backwards yet.
        if ( animation.age < 0 ) return;

        final com.badlogic.gdx.graphics.g2d.Animation gdxanim = abstractAssetSystem.get(id);
        if ( gdxanim == null) return;

        final TextureRegion frame = gdxanim.getKeyFrame(animation.age, animation.loop);

        if ( animation.flippedX)
        {
            // mirror
            batch.draw(frame.getTexture(),
                    roundToPixels(position.xy.x),
                    roundToPixels(position.xy.y),
                    angle.ox == Angle.ORIGIN_AUTO ? frame.getRegionWidth() * scale * 0.5f : angle.ox,
                    angle.oy == Angle.ORIGIN_AUTO ? frame.getRegionHeight() * scale * 0.5f : angle.oy,
                    frame.getRegionWidth() * scale,
                    frame.getRegionHeight() * scale,
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
                    roundToPixels(position.xy.x),
                    roundToPixels(position.xy.y),
                    angle.ox == Angle.ORIGIN_AUTO ? frame.getRegionWidth() * scale * 0.5f : angle.ox,
                    angle.oy == Angle.ORIGIN_AUTO ? frame.getRegionHeight() * scale * 0.5f : angle.oy,
                    frame.getRegionWidth() * scale,
                    frame.getRegionHeight() * scale, 1, 1,
                    angle.rotation);
        } else {
            batch.draw(frame,
                    roundToPixels(position.xy.x),
                    roundToPixels(position.xy.y),
                    frame.getRegionWidth() * scale,
                    frame.getRegionHeight() * scale);
        }
    }
}

package net.mostlyoriginal.api.system.render;

/**
 * @author Daan van Yperen
 */

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Wire;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.mostlyoriginal.api.component.basic.Angle;
import net.mostlyoriginal.api.component.graphics.Color;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.graphics.Anim;
import net.mostlyoriginal.api.manager.AbstractAssetSystem;
import net.mostlyoriginal.api.system.camera.CameraSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Render and progress animations.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.graphics.Anim
 */
@Wire
public class AnimRenderSystem extends EntitySystem {

    protected ComponentMapper<Pos> mPos;
    protected ComponentMapper<Anim> mAnim;
    protected ComponentMapper<Color> mColor;
    protected ComponentMapper<Angle> mAngle;
    protected CameraSystem cameraSystem;
    protected AbstractAssetSystem abstractAssetSystem;

    protected SpriteBatch batch;
    protected final List<Entity> sortedEntities = new ArrayList<Entity>();
    public boolean sortedDirty = false;

    public Comparator<Entity> layerSortComperator = new Comparator<Entity>() {
        @Override
        public int compare(Entity e1, Entity e2) {
            return mAnim.get(e1).layer - mAnim.get(e2).layer;
        }
    };

    private float age;

    public AnimRenderSystem() {
        super(Aspect.getAspectForAll(Pos.class, Anim.class));
        batch  = new SpriteBatch(2000);
    }

    @Override
    protected void begin() {

        age += world.delta;

        batch.setProjectionMatrix(cameraSystem.camera.combined);
        batch.begin();
    }

    @Override
    protected void end() {
        batch.end();
    }

    @Override
    protected void processEntities(ImmutableBag<Entity> entities) {

        if (sortedDirty) {
            sortedDirty = false;
            Collections.sort(sortedEntities, layerSortComperator);
        }

        for (Entity entity : sortedEntities) {
            process(entity);
        }
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

        drawAnimation(anim, angle, pos, anim.id);
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
                    (int)position.x,
                    (int)position.y,
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
                    (int)position.x,
                    (int)position.y,
                    angle.ox == Angle.ORIGIN_AUTO ? frame.getRegionWidth() * animation.scale * 0.5f : angle.ox,
                    angle.oy == Angle.ORIGIN_AUTO ? frame.getRegionHeight() * animation.scale * 0.5f : angle.oy,
                    frame.getRegionWidth() * animation.scale,
                    frame.getRegionHeight() * animation.scale, 1, 1,
                    angle.rotation);
        } else {
            batch.draw(frame,
                    (int)position.x,
                    (int)position.y,
                    frame.getRegionWidth() * animation.scale,
                    frame.getRegionHeight() * animation.scale);
        }
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }

    @Override
    protected void inserted(Entity e) {
        sortedEntities.add(e);
        sortedDirty = true;
    }

    @Override
    protected void removed(Entity e) {
        sortedEntities.remove(e);
    }
}

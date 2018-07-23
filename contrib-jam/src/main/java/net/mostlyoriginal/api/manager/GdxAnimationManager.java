package net.mostlyoriginal.api.manager;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.mostlyoriginal.api.component.basic.Size;
import net.mostlyoriginal.api.component.graphics.Animation;
import net.mostlyoriginal.api.component.graphics.AnimationAsset;

import java.util.HashMap;
import java.util.Map;

/**
 * Animation manager.
 *
 * @author Daan van Yperen
 */
public class GdxAnimationManager extends AssetManager<Animation, AnimationAsset> {

    protected ComponentMapper<Size> mSize;
    public HashMap<String, com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>>
            library = new HashMap<>();

    public GdxAnimationManager() {
        super(Animation.class, AnimationAsset.class);
    }

    /**
     * Add animation.
     */
    public void add(String name, com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> anim) {
        library.put(name, anim);
    }

    /**
     * Add animations.
     * @param value
     */
    public void addAll(Map<String, ? extends com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>> value) {
        library.putAll(value);
    }

    @Override
    protected void setup(int e, Animation anim, AnimationAsset animAsset) {
        animAsset.asset = library.get(anim.id);

        if (animAsset.asset == null) {
            throw new RuntimeException("No such animation, '" + anim.id + "'");
        }

        // set size to asset.
        if (!mSize.has(e)) {
            TextureRegion frame = animAsset.asset.getKeyFrame(0);
            mSize.create(e).set(
                    frame.getRegionWidth(),
                    frame.getRegionHeight());
        }
    }
}

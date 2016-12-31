package net.mostlyoriginal.api.component.graphics;

import com.artemis.PooledComponent;
import com.artemis.annotations.Fluid;
import com.artemis.annotations.Transient;
import com.badlogic.gdx.graphics.g2d.*;

/**
 * @author Daan van Yperen
 */
@Fluid(exclude = true)
@Transient
public class AnimationAsset extends PooledComponent {

    public com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> asset;

    @Override
    protected void reset() {
        asset = null;
    }
}

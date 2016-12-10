package net.mostlyoriginal.api.component.graphics;

import com.artemis.PooledComponent;
import com.artemis.annotations.Fluid;
import com.artemis.annotations.Transient;
import com.artemis.annotations.Fluid;
import com.artemis.annotations.Transient;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Daan van Yperen
 */
@Fluid(exclude = true)
@Transient
public class SpriteAsset extends PooledComponent {

    public Texture asset;

    @Override
    protected void reset() {
        asset = null;
    }
}

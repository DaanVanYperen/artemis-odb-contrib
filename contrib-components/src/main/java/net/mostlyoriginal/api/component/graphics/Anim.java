package net.mostlyoriginal.api.component.graphics;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;
import net.mostlyoriginal.api.Depends;
import net.mostlyoriginal.api.component.basic.Pos;

/**
 * Entity displays a depth sorted animation.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.system.render.AnimRenderSystem
 */
@Depends(Pos.class)
public class Anim extends Component {

    public boolean flippedX; // flip animation, should not affect rotation.

    /** Scale of animation */
    /* @Todo separate into own component. */
    public float scale = 1;

    /** recolor */
    /* @Todo separate into own component? */
    public final Color color = new Color(1, 1, 1, 1);

    /** target layer, higher is in front, lower is behind. */
    public int layer = 0;
    /** Playback speed factor. */
    public float speed = 1;
    /** Animation progression */
    public float age = 0;
    /** Loop animation when the end has been reached. */
    public boolean loop = true;

    /** Animation identifier. */
    /** write asset resolver */
    public String id;

    public Anim(String id) {
        this.id = id;
    }

    public Anim(String id, int layer) {
        this.id = id;
        this.layer = layer;
    }

    public Anim() {
    }
}

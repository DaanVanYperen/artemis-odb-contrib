package net.mostlyoriginal.api.component.graphics;

import com.artemis.Component;

/**
 * Indicate this entity is renderable.
 *
 * Combine with specialized renderables, like Anim or Label.
 *
 * @author Daan van Yperen
 * @see Anim
 */
public class Renderable extends Component {

    /** target layer, higher is in front, lower is behind. */
    public int layer = 0;

    public Renderable() {
    }

    public Renderable(int layer) {
        this.layer = layer;
    }
}

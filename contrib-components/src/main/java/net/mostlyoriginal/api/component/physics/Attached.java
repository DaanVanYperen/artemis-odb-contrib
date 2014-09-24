package net.mostlyoriginal.api.component.physics;

import com.artemis.Component;
import net.mostlyoriginal.api.utils.reference.EntityReference;

/**
 * Attach entity to parent entity.
 *
 * @author Daan van Yperen
 */
public class Attached extends Component {

    public EntityReference parent;

    // xo + parent x = entity x
    public int xo;
    // yo + parent y = entity y
    public int yo;

    // slack, like recoil on a weapon.
    // max length of the slack vector. like weapon recoil.
    public float maxSlack = 10;
    // slack offset X
    public float slackX;
    // slack offset Y
    public float slackY;
    // Tension on the spring to return to its original state. 1= really slow.
    public float tension = 30;

    public Attached(EntityReference parent) {
        this.parent = parent;
    }

    /**
     * @param parent
     * @param xo X offset relative to parent X.
     * @param yo Y offset relative to parent Y.
     */
    public Attached(EntityReference parent, int xo, int yo) {
        this.parent = parent;
        this.xo = xo;
        this.yo = yo;
    }
}

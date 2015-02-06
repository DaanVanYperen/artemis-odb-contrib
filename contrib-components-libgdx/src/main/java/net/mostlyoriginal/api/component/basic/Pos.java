package net.mostlyoriginal.api.component.basic;

import com.artemis.Component;

import java.io.Serializable;

/**
 * @author Daan van Yperen
 */
public class Pos extends Component implements Serializable {
    public float x;
    public float y;

    public Pos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Pos() {
    }
}

package net.mostlyoriginal.api.component.graphics;

import com.artemis.Component;

/**
 * Color for animations, labels.
 *
 * Optional, convention is to assume white if not set.
 *
 * @author Daan van Yperen
 */
public class Color extends Component {

    public float r;
    public float g;
    public float b;
    public float a;

    public Color() {
    }

    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public void set(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public void set(Color color)
    {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
        this.a = color.a;
    }
}

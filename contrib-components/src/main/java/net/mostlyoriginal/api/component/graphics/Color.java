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

    public Color(Color color) {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
        this.a = color.a;
    }

	/** Create Color by hex, RRGGBBAA. */
	public Color(String hex) {
		setHex(hex);
	}

	/** Set color to hex, RRGGBBAA. */
	public void setHex(String hex) {
		set((float) Integer.valueOf(hex.substring(0, 2), 16) / 255.0F,
				(float) Integer.valueOf(hex.substring(2, 4), 16) / 255.0F,
				(float) Integer.valueOf(hex.substring(4, 6), 16) / 255.0F,
				(float) (hex.length() != 8 ? 255 : Integer.valueOf(hex.substring(6, 8), 16)) / 255.0F);
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

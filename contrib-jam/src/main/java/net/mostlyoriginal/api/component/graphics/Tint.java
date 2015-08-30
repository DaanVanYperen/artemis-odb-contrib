package net.mostlyoriginal.api.component.graphics;

import net.mostlyoriginal.api.component.common.ExtendedComponent;

/**
 * Tint for animations, labels.
 *
 * Optional, convention is to assume white if not set.
 *
 * @author Daan van Yperen
 */
public class Tint extends ExtendedComponent<Tint> {

    public float r;
    public float g;
    public float b;
    public float a;

    public Tint() {
    }

    @Override
    protected void reset() {

    }

    public Tint(Tint tint) {
        this.r = tint.r;
        this.g = tint.g;
        this.b = tint.b;
        this.a = tint.a;
    }

	/** Create Tint by hex, RRGGBBAA. */
	public Tint(String hex) {
		setHex(hex);
	}

	/** Set color to hex, RRGGBBAA. */
	public void setHex(String hex) {
		set((float) Integer.valueOf(hex.substring(0, 2), 16) / 255.0F,
				(float) Integer.valueOf(hex.substring(2, 4), 16) / 255.0F,
				(float) Integer.valueOf(hex.substring(4, 6), 16) / 255.0F,
				(float) (hex.length() != 8 ? 255 : Integer.valueOf(hex.substring(6, 8), 16)) / 255.0F);
	}

	public Tint(float r, float g, float b, float a) {
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

    public void set(Tint tint)
    {
        this.r = tint.r;
        this.g = tint.g;
        this.b = tint.b;
        this.a = tint.a;
    }
}

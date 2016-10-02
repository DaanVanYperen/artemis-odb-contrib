package net.mostlyoriginal.api.component.basic;

import com.artemis.annotations.Fluid;
import com.badlogic.gdx.math.Interpolation;
import net.mostlyoriginal.api.component.common.ExtendedComponent;
import net.mostlyoriginal.api.component.common.Tweenable;

/**
 * @author Daan van Yperen
 * @deprecated behaviour not clearly defined at this time, avoid.*/
@Deprecated
public class Scale extends ExtendedComponent<Scale> implements Tweenable<Scale> {

	public static final Scale DEFAULT = new Scale(1);
	public float scale = 1f;

	public Scale() {}
	public Scale(float scale) {
		this.scale=scale;
	}

	@Override
	protected void reset() {
		scale = 1f;
	}

	@Override
	public void set(Scale scale) {
		this.scale = scale.scale;
	}

	public void set(float scale) {
		this.scale = scale;
	}


	@Override
	public void tween(Scale a, Scale b, float value) {
		scale = Interpolation.linear.apply(a.scale, b.scale, value);
	}
}

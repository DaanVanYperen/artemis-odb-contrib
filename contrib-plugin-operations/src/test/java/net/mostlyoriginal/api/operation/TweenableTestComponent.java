    package net.mostlyoriginal.api.operation;

import com.artemis.Component;
import com.badlogic.gdx.math.Interpolation;
import net.mostlyoriginal.api.component.common.Tweenable;

/**
 * @author Daan van Yperen
 */
public class TweenableTestComponent extends Component implements Tweenable<TweenableTestComponent> {

	public float val;

	public TweenableTestComponent() {
	}

	public TweenableTestComponent(float val) {
		this.val = val;
	}

	@Override
	public TweenableTestComponent tween(TweenableTestComponent a, TweenableTestComponent b, float value) {
		this.val = Interpolation.linear.apply(a.val, b.val, value);
		return this;
	}
}

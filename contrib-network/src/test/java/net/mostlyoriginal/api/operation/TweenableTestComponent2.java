package net.mostlyoriginal.api.operation;

import com.artemis.Component;
import com.badlogic.gdx.math.Interpolation;
import net.mostlyoriginal.api.component.common.Tweenable;

/**
 * @author Daan van Yperen
 */
public class TweenableTestComponent2 extends Component implements Tweenable<TweenableTestComponent2> {

	public float val;

	public TweenableTestComponent2(float val) {
		this.val = val;
	}

	@Override
	public TweenableTestComponent2 tween(TweenableTestComponent2 a, TweenableTestComponent2 b, float value) {
		this.val = Interpolation.linear.apply(a.val, b.val, value);
		return this;
	}
}

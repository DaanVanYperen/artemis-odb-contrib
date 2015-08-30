package net.mostlyoriginal.api.component.common;

import com.artemis.Component;

/**
 * Support tweening between two component states.
 *
 * @author Daan van Yperen
 */
public interface Tweenable<T extends Component & Tweenable<T>> {

	/**
	 * Set to tween of two component states. Uses linear tween.
	 *
	 * @param a start state at zero.
	 * @param b end state at one.
	 * @param value tween (0..1)
	 * @return {@code this}
	 */
	 T tween(T a, T b, float value);
}

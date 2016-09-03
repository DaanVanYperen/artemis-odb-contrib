package net.mostlyoriginal.api.operation;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.graphics.Tint;
import net.mostlyoriginal.api.operation.common.Operation;
import net.mostlyoriginal.api.operation.temporal.TweenOperation;

/**
 * @author Daan van Yperen
 */
public class JamOperationFactory {
	private JamOperationFactory() {
	}

	/**
	 * Linear tween for Pos.
	 *
	 * @param x1       start X
	 * @param y1       start Y
	 * @param x2       end X
	 * @param y2       end Y
	 * @param duration duration of tween, in seconds.
	 */
	public static TweenPosOperation moveBetween(float x1, float y1, float x2, float y2, float duration) {
		return moveBetween(x1, y1, x2, y2, duration, Interpolation.linear);
	}

	/**
	 * Linear tween for Pos.
	 *
	 * @param from     start
	 * @param to       end
	 * @param duration duration of tween, in seconds.
	 */
	public static TweenPosOperation moveBetween(Vector2 from, Vector2 to, float duration) {
		return moveBetween(from, to, duration, Interpolation.linear);
	}

	/**
	 * Linear tween for Pos.
	 *
	 * @param from     start
	 * @param to       end
	 * @param duration duration of tween, in seconds.
	 */
	public static TweenPosOperation moveBetween(Pos from, Pos to, float duration) {
		return moveBetween(from, to, duration, Interpolation.linear);
	}

	/**
	 * Linear tween for Pos.
	 *
	 * @param x1            start X
	 * @param y1            start Y
	 * @param x2            end X
	 * @param y2            end Y
	 * @param duration      duration of tween, in seconds.
	 * @param interpolation method of interpolation.
	 */
	public static TweenPosOperation moveBetween(float x1, float y1, float x2, float y2, float duration, Interpolation interpolation) {
		final TweenPosOperation operation = Operation.prepare(TweenPosOperation.class);
		operation.setup(interpolation, duration);
		operation.getFrom().set(x1, y1);
		operation.getTo().set(x2, y2);
		return operation;
	}

	/**
	 * Linear tween for Pos.
	 *
	 * @param from          start
	 * @param to            end
	 * @param duration      duration of tween, in seconds.
	 * @param interpolation method of interpolation.
	 */
	public static TweenPosOperation moveBetween(Vector2 from, Vector2 to, float duration, Interpolation interpolation) {
		return moveBetween(from.x, from.y, to.x, to.y, duration, interpolation);
	}

	/**
	 * Linear tween for Pos.
	 *
	 * @param from          start
	 * @param to            end
	 * @param duration      duration of tween, in seconds.
	 * @param interpolation method of interpolation.
	 */
	public static TweenPosOperation moveBetween(Pos from, Pos to, float duration, Interpolation interpolation) {
		return moveBetween(from.xy.x, from.xy.y, to.xy.x, to.xy.y, duration, interpolation);
	}

	/**
	 * Instantly move to coords.
	 */
	public static SetPosOperation moveTo(float x, float y) {
		final SetPosOperation operation = Operation.prepare(SetPosOperation.class);
		operation.get().set(x, y);
		return operation;
	}

	/**
	 * Instantly move to coords {@see Pos}.
	 */
	public static SetPosOperation moveTo(Pos pos) {
		return moveTo(pos.xy.x, pos.xy.y);
	}

	/**
	 * Instantly move to coords {@see Pos}.
	 */
	public static SetPosOperation moveTo(Vector2 v) {
		return moveTo(v.x, v.y);
	}


	/**
	 * Linear scale between pos.
	 *
	 * @param from          start scale
	 * @param to            end scale
	 * @param duration      duration of tween, in seconds.
	 * @param interpolation method of interpolation.
	 */
	public static TweenScaleOperation scaleBetween(float from, float to, float duration, Interpolation interpolation) {
		final TweenScaleOperation operation = Operation.prepare(TweenScaleOperation.class);
		operation.setup(interpolation, duration);
		operation.getFrom().scale = from;
		operation.getTo().scale = to;
		return operation;
	}

	/**
	 * Linear scale between pos.
	 *
	 * @param from     start scale
	 * @param to       end scale
	 * @param duration duration of tween, in seconds.
	 */
	public static TweenScaleOperation scaleBetween(float from, float to, float duration) {
		return scaleBetween(from, to, duration, Interpolation.linear);
	}

	/**
	 * Instantly scale to coords {@see Pos}.
	 */
	public static SetScaleOperation scaleTo(float scale) {
		final SetScaleOperation operation = Operation.prepare(SetScaleOperation.class);
		operation.get().scale = scale;
		return operation;
	}

	/**
	 * Set tint.
	 *
	 * @param to            end
	 */
	public static SetTintOperation tintTo(Tint to) {
		SetTintOperation operation = Operation.prepare(SetTintOperation.class);
		operation.get().set(to);
		return operation;
	}

	/**
	 * Linear tween for tint (color).
	 *
	 * @param from          start
	 * @param to            end
	 * @param duration      duration of tween, in seconds.
	 */
	public static TweenTintOperation tintBetween(Tint from, Tint to, float duration) {
		return tintBetween(from, to, duration, Interpolation.linear);
	}

	/**
	 * Tween for tint (color).
	 *
	 * @param from          start
	 * @param to            end
	 * @param duration      duration of tween, in seconds.
	 * @param interpolation method of interpolation.
	 */
	public static TweenTintOperation tintBetween(Tint from, Tint to, float duration, Interpolation interpolation) {
		final TweenTintOperation operation = prepTween(TweenTintOperation.class, duration, interpolation);
		operation.getFrom().set(from);
		operation.getTo().set(to);
		return operation;
	}

	private static Tint fromTmp = new Tint();
	private static Tint toTmp = new Tint();

	private static <T extends TweenOperation> T prepTween(Class<T> operationType, float duration, Interpolation interpolation) {
		final T operation = Operation.prepare(operationType);
		operation.setup(interpolation, duration);
		return operation;
	}
}
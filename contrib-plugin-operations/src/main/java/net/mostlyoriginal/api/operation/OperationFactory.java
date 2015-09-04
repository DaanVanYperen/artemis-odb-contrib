package net.mostlyoriginal.api.operation;

import com.artemis.Component;
import com.badlogic.gdx.math.Interpolation;
import net.mostlyoriginal.api.component.common.Tweenable;
import net.mostlyoriginal.api.operation.act.*;
import net.mostlyoriginal.api.operation.common.Operation;
import net.mostlyoriginal.api.operation.flow.ParallelOperation;
import net.mostlyoriginal.api.operation.flow.SerialOperation;
import net.mostlyoriginal.api.utils.Preconditions;

/**
 * @author Daan van Yperen
 */
public class OperationFactory {
	private OperationFactory() {}

	public static ParallelOperation parallel( Operation o1 ) {
		final ParallelOperation operation = Operation.prepare(ParallelOperation.class);
		operation.add(o1);
		return operation;
	}

	public static ParallelOperation parallel( Operation o1, Operation o2 ) {
		final ParallelOperation operation = Operation.prepare(ParallelOperation.class);
		operation.addAll(o1, o2);
		return operation;
	}

	public static ParallelOperation parallel( Operation o1, Operation o2, Operation o3 ) {
		final ParallelOperation operation = Operation.prepare(ParallelOperation.class);
		operation.addAll(o1, o2, o3);
		return operation;
	}

	public static ParallelOperation parallel( Operation o1, Operation o2, Operation o3, Operation o4 ) {
		final ParallelOperation operation = Operation.prepare(ParallelOperation.class);
		operation.addAll(o1, o2, o3, o4);
		return operation;
	}

	public static ParallelOperation parallel( Operation o1, Operation o2, Operation o3, Operation o4, Operation o5 ) {
		final ParallelOperation operation = Operation.prepare(ParallelOperation.class);
		operation.addAll(o1, o2, o3, o4, o5);
		return operation;
	}


	public static SerialOperation sequence(Operation o1) {
		final SerialOperation operation = Operation.prepare(SerialOperation.class);
		operation.add(o1);
		return operation;
	}

	public static SerialOperation sequence(Operation o1, Operation o2) {
		final SerialOperation operation = Operation.prepare(SerialOperation.class);
		operation.addAll(o1, o2);
		return operation;
	}

	public static SerialOperation sequence(Operation o1, Operation o2, Operation o3) {
		final SerialOperation operation = Operation.prepare(SerialOperation.class);
		operation.addAll(o1, o2, o3);
		return operation;
	}

	public static SerialOperation sequence(Operation o1, Operation o2, Operation o3, Operation o4) {
		final SerialOperation operation = Operation.prepare(SerialOperation.class);
		operation.addAll(o1, o2, o3, o4);
		return operation;
	}

	public static SerialOperation sequence(Operation o1, Operation o2, Operation o3, Operation o4, Operation o5) {
		final SerialOperation operation = Operation.prepare(SerialOperation.class);
		operation.addAll(o1, o2, o3, o4, o5);
		return operation;
	}

	public static Operation deleteFromWorld() {
		return Operation.prepare(DeleteFromWorldOperation.class);
	}

	public static DelayOperation delay( float delay )
	{
		Preconditions.checkArgument(delay >= 0, "Delay must be >= 0.");
		DelayOperation operation = Operation.prepare(DelayOperation.class);
		operation.setDelay(delay);
		return operation;
	}

	public static AddOperation add(Component component)
	{
		Preconditions.checkNotNull(component);
		AddOperation operation = Operation.prepare(AddOperation.class);
		operation.component = component;
		return operation;
	}

	public static RemoveOperation remove(Class<? extends Component> componentClass)
	{
		Preconditions.checkNotNull(componentClass);
		RemoveOperation operation = Operation.prepare(RemoveOperation.class);
		operation.componentClass = componentClass;
		return operation;
	}

	/**
	 * Setup linear tween between two component states.
	 *
	 * @param a component a starting state. Tweening does not release pooled components after use.
	 * @param b component b starting state. Tweening does not release pooled components after use.
	 * @param duration duration of tween, in seconds.
	 */
	public static <T extends Component & Tweenable<T>> TweenOperation tween(T a, T b, float duration)
	{
		return tween(a,b,duration,Interpolation.linear);
	}

	/**
	 * Setup tween between two component states.
	 *
	 * @param a component a starting state. Tweening does not release pooled components after use.
	 * @param b component b starting state. Tweening does not release pooled components after use.
	 * @param duration duration of tween, in seconds.
	 * @param interpolation method of interpolation.
	 */
	public static <T extends Component & Tweenable<T>> TweenOperation tween(T a, T b, float duration, Interpolation interpolation)
	{
		final TweenOperation operation = Operation.prepare(TweenOperation.class);
		operation.setup(a,b,interpolation,duration);
		return operation;
	}
}

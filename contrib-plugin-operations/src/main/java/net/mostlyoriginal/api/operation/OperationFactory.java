package net.mostlyoriginal.api.operation;

import com.artemis.Component;
import com.badlogic.gdx.math.Interpolation;
import net.mostlyoriginal.api.component.common.Tweenable;
import net.mostlyoriginal.api.operation.basic.AddOperation;
import net.mostlyoriginal.api.operation.basic.DeleteFromWorldOperation;
import net.mostlyoriginal.api.operation.basic.RemoveOperation;
import net.mostlyoriginal.api.operation.common.BasicOperation;
import net.mostlyoriginal.api.operation.common.Operation;
import net.mostlyoriginal.api.operation.flow.ParallelOperation;
import net.mostlyoriginal.api.operation.flow.RepeatOperation;
import net.mostlyoriginal.api.operation.flow.SequenceOperation;
import net.mostlyoriginal.api.operation.temporal.DelayOperation;
import net.mostlyoriginal.api.operation.temporal.TweenOperation;
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


	public static SequenceOperation sequence(Operation o1) {
		final SequenceOperation operation = Operation.prepare(SequenceOperation.class);
		operation.add(o1);
		return operation;
	}

	public static SequenceOperation sequence(Operation o1, Operation o2) {
		final SequenceOperation operation = Operation.prepare(SequenceOperation.class);
		operation.addAll(o1, o2);
		return operation;
	}

	public static SequenceOperation sequence(Operation o1, Operation o2, Operation o3) {
		final SequenceOperation operation = Operation.prepare(SequenceOperation.class);
		operation.addAll(o1, o2, o3);
		return operation;
	}

	public static SequenceOperation sequence(Operation o1, Operation o2, Operation o3, Operation o4) {
		final SequenceOperation operation = Operation.prepare(SequenceOperation.class);
		operation.addAll(o1, o2, o3, o4);
		return operation;
	}

	public static SequenceOperation sequence(Operation o1, Operation o2, Operation o3, Operation o4, Operation o5) {
		final SequenceOperation operation = Operation.prepare(SequenceOperation.class);
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
		operation.setDuration(delay);
		return operation;
	}

	public static AddOperation add(Component component)
	{
		Preconditions.checkNotNull(component);
		AddOperation operation = Operation.prepare(AddOperation.class);
		operation.component = component;
		return operation;
	}

	public static BasicOperation remove(Class<? extends Component> componentClass)
	{
		Preconditions.checkNotNull(componentClass);
		RemoveOperation operation = Operation.prepare(RemoveOperation.class);
		operation.componentClass = componentClass;
		return operation;
	}

	/**
	 * Setup linear tween between two component states.
	 *
	 * From/to states are not pool managed, and will be garbage collected.
	 *
	 * @param a component a starting state.
	 * @param b component b starting state.
	 * @param duration duration of tween, in seconds.
	 */
	public static <T extends Component & Tweenable<T>> TweenOperation tween(T a, T b, float duration)
	{
		return tween(a,b,duration,Interpolation.linear);
	}

	/**
	 * Setup tween between two component states.
	 *
	 * From/to states are not pool managed, and will be garbage collected.
	 *
	 * @param a component a starting state.
	 * @param b component b starting state.
	 * @param duration duration of tween, in seconds.
	 * @param interpolation method of interpolation.
	 */
	public static <T extends Component & Tweenable<T>> TweenOperation tween(T a, T b, float duration, Interpolation interpolation)
	{
		final TweenOperation operation = Operation.prepare(TweenOperation.class);
		operation.setup(a,b,interpolation,duration);
		return operation;
	}

	/**
	 * Repeat nested operation one or more times.
	 *
	 * @param operation operation to repeat.
	 * @param repetitions number of times to repeat.
	 * @return {@see RepeatOperation}
	 */
	public static RepeatOperation repeat(int repetitions, Operation operation) {
		RepeatOperation repeatOperation = Operation.prepare(RepeatOperation.class);
		repeatOperation.setup(operation, repetitions);
		return repeatOperation;
	}

	/**
	 * Repeat nested operation forever.
	 *
	 * Note that indefinitely repeated operations will block parent {@see #sequence}
	 * from reaching the next step.
	 *
	 * @param operation operation to repeat.
	 * @return {@see RepeatOperation}
	 */
	public static RepeatOperation repeat(Operation operation) {
		RepeatOperation repeatOperation = Operation.prepare(RepeatOperation.class);
		repeatOperation.setup(operation);
		return repeatOperation;
	}
}

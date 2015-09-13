package net.mostlyoriginal.api.operation;

import com.artemis.Component;
import com.artemis.PackedComponent;
import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import net.mostlyoriginal.api.component.common.Tweenable;
import net.mostlyoriginal.api.operation.basic.DeleteFromWorldOperation;
import net.mostlyoriginal.api.operation.basic.LegacyAddOperation;
import net.mostlyoriginal.api.operation.basic.RemoveOperation;
import net.mostlyoriginal.api.operation.basic.UnpooledMirrorOperation;
import net.mostlyoriginal.api.operation.common.Operation;
import net.mostlyoriginal.api.operation.flow.ParallelOperation;
import net.mostlyoriginal.api.operation.flow.RepeatOperation;
import net.mostlyoriginal.api.operation.flow.SequenceOperation;
import net.mostlyoriginal.api.operation.temporal.DelayOperation;
import net.mostlyoriginal.api.operation.temporal.UnpooledTweenOperation;
import net.mostlyoriginal.api.utils.Preconditions;

/**
 * Entity/component operations.
 *
 * Statically import then:
 *
 * <code>
 * repeat(
 *       sequential(
 *          tween(posa, posb, seconds(5)),
 *          delay(seconds(10)),
 *       )
 * )
 * </code>
 *
 * @author Daan van Yperen
 */
public class OperationFactory {
	private OperationFactory() {}

	/**
	 * Runs operation(s) in parallel.
	 *
	 * @param o1 operation
	 * @return {@see ParallelOperation}
	 */
	public static ParallelOperation parallel( Operation o1 ) {
		final ParallelOperation operation = Operation.prepare(ParallelOperation.class);
		operation.add(o1);
		return operation;
	}

	/**
	 * Runs operation(s) in parallel.
	 *
	 * @param o1 operation
	 * @param o2 operation
	 * @return {@see ParallelOperation}
	 */
	public static ParallelOperation parallel( Operation o1, Operation o2 ) {
		final ParallelOperation operation = Operation.prepare(ParallelOperation.class);
		operation.addAll(o1, o2);
		return operation;
	}

	/**
	 * Runs operation(s) in parallel.
	 *
	 * @param o1 operation
	 * @param o2 operation
	 * @param o3 operation
	 * @return {@see ParallelOperation}
	 */
	public static ParallelOperation parallel( Operation o1, Operation o2, Operation o3 ) {
		final ParallelOperation operation = Operation.prepare(ParallelOperation.class);
		operation.addAll(o1, o2, o3);
		return operation;
	}

	/**
	 * Runs operation(s) in parallel.
	 *
	 * @param o1 operation
	 * @param o2 operation
	 * @param o3 operation
	 * @param o4 operation
	 * @return {@see ParallelOperation}
	 */
	public static ParallelOperation parallel( Operation o1, Operation o2, Operation o3, Operation o4 ) {
		final ParallelOperation operation = Operation.prepare(ParallelOperation.class);
		operation.addAll(o1, o2, o3, o4);
		return operation;
	}

	/**
	 * Runs operation(s) in parallel.
	 *
	 * @param o1 operation
	 * @param o2 operation
	 * @param o3 operation
	 * @param o4 operation
	 * @param o5 operation
	 * @return {@see ParallelOperation}
	 */
	public static ParallelOperation parallel( Operation o1, Operation o2, Operation o3, Operation o4, Operation o5 ) {
		final ParallelOperation operation = Operation.prepare(ParallelOperation.class);
		operation.addAll(o1, o2, o3, o4, o5);
		return operation;
	}

	/**
	 * Runs operation(s) sequentially.
	 *
	 * @param o1 operation
	 * @return {@see SequenceOperation}
	 */
	public static SequenceOperation sequence(Operation o1) {
		final SequenceOperation operation = Operation.prepare(SequenceOperation.class);
		operation.add(o1);
		return operation;
	}

	/**
	 * Runs operation(s) sequentially.
	 *
	 * @param o1 operation
	 * @param o2 operation
	 * @return {@see SequenceOperation}
	 */
	public static SequenceOperation sequence(Operation o1, Operation o2) {
		final SequenceOperation operation = Operation.prepare(SequenceOperation.class);
		operation.addAll(o1, o2);
		return operation;
	}

	/**
	 * Runs operation(s) sequentially.
	 *
	 * @param o1 operation
	 * @param o2 operation
	 * @param o3 operation
	 * @return {@see SequenceOperation}
	 */
	public static SequenceOperation sequence(Operation o1, Operation o2, Operation o3) {
		final SequenceOperation operation = Operation.prepare(SequenceOperation.class);
		operation.addAll(o1, o2, o3);
		return operation;
	}

	/**
	 * Runs operation(s) sequentially.
	 *
	 * @param o1 operation
	 * @param o2 operation
	 * @param o3 operation
	 * @param o4 operation
	 * @return {@see SequenceOperation}
	 */
	public static SequenceOperation sequence(Operation o1, Operation o2, Operation o3, Operation o4) {
		final SequenceOperation operation = Operation.prepare(SequenceOperation.class);
		operation.addAll(o1, o2, o3, o4);
		return operation;
	}

	/**
	 * Runs operation(s) sequentially.
	 *
	 * @param o1 operation
	 * @param o2 operation
	 * @param o3 operation
	 * @param o4 operation
	 * @param o5 operation
	 * @return {@see SequenceOperation}
	 */
	public static SequenceOperation sequence(Operation o1, Operation o2, Operation o3, Operation o4, Operation o5) {
		final SequenceOperation operation = Operation.prepare(SequenceOperation.class);
		operation.addAll(o1, o2, o3, o4, o5);
		return operation;
	}

	/**
	 * Deletes entity from world.
	 *
	 * Will wipe all future operations.
	 *
	 * @return {@see DeleteFromWorldOperation}
	 */
	public static DeleteFromWorldOperation deleteFromWorld() {
		return Operation.prepare(DeleteFromWorldOperation.class);
	}

	/**
	 * Delay next operation.
	 *
	 * Example: delay(seconds(5))
	 *
	 * @param delay
	 * @return {@see DelayOperation}
	 */
	public static DelayOperation delay( float delay )
	{
		Preconditions.checkArgument(delay >= 0, "Delay must be >= 0.");
		final DelayOperation operation = Operation.prepare(DelayOperation.class);
		operation.setDuration(delay);
		return operation;
	}

	/**
	 * Add component instance to entity.
	 *
	 * Not compatible with pooled components. Do not use this if you want to avoid GC!
	 *
	 * @see UnpooledMirrorOperation for a poolable solution.
	 * @param component
	 * @return {@see LegacyAddOperation}
	 */
	@Deprecated
	public static LegacyAddOperation add(Component component)
	{
		Preconditions.checkNotNull(component);
		Preconditions.checkArgument(!ClassReflection.isAssignableFrom(PooledComponent.class, component.getClass()), "Does not support Pooled components.");
		Preconditions.checkArgument(!ClassReflection.isAssignableFrom(PackedComponent.class, component.getClass()), "Does not support Packed components.");
		final LegacyAddOperation operation = Operation.prepare(LegacyAddOperation.class);
		operation.component = component;
		return operation;
	}

	/**
	 * Remove class from entity.
	 *
	 * @param componentClass component to remove.
	 * @return {@see RemoveOperation}
	 */
	public static RemoveOperation remove(Class<? extends Component> componentClass)
	{
		Preconditions.checkNotNull(componentClass);
		final RemoveOperation operation = Operation.prepare(RemoveOperation.class);
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
	public static <T extends Component & Tweenable<T>> UnpooledTweenOperation tween(T a, T b, float duration)
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
	public static <T extends Component & Tweenable<T>> UnpooledTweenOperation tween(T a, T b, float duration, Interpolation interpolation)
	{
		final UnpooledTweenOperation operation = Operation.prepare(UnpooledTweenOperation.class);
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

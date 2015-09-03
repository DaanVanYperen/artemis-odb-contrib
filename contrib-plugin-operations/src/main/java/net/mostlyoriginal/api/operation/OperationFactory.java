package net.mostlyoriginal.api.operation;

import net.mostlyoriginal.api.operation.act.DelayOperation;
import net.mostlyoriginal.api.operation.act.DeleteFromWorldOperation;
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


	public static SerialOperation chain( Operation o1 ) {
		final SerialOperation operation = Operation.prepare(SerialOperation.class);
		operation.add(o1);
		return operation;
	}

	public static SerialOperation chain( Operation o1, Operation o2 ) {
		final SerialOperation operation = Operation.prepare(SerialOperation.class);
		operation.addAll(o1, o2);
		return operation;
	}

	public static SerialOperation chain( Operation o1, Operation o2, Operation o3 ) {
		final SerialOperation operation = Operation.prepare(SerialOperation.class);
		operation.addAll(o1, o2, o3);
		return operation;
	}

	public static SerialOperation chain( Operation o1, Operation o2, Operation o3, Operation o4 ) {
		final SerialOperation operation = Operation.prepare(SerialOperation.class);
		operation.addAll(o1, o2, o3, o4);
		return operation;
	}

	public static SerialOperation chain( Operation o1, Operation o2, Operation o3, Operation o4, Operation o5 ) {
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
}

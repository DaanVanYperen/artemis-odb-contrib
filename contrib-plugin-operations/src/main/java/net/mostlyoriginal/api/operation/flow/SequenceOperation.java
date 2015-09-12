package net.mostlyoriginal.api.operation.flow;

import com.artemis.Entity;
import net.mostlyoriginal.api.operation.common.Operation;
import net.mostlyoriginal.api.operation.common.OperationFlow;
import net.mostlyoriginal.api.utils.Preconditions;

/**
 * Fire operations one at a time.
 * <p/>
 * Calls in order. Will fire at most 1 operation per process.
 *
 * @author Daan van Yperen
 */
public class SequenceOperation extends OperationFlow {

	int index = 0;

	public SequenceOperation() {
	}

	@Override
	public boolean process(float delta, Entity e) {
		if (index < operations.size) {
			nextOperation(delta, e, operations.get(index));
		}

		if (index >= operations.size) {
			completed = true;
		}

		return completed;
	}

	protected void nextOperation(float delta, Entity e, Operation operation) {
		Preconditions.checkArgument(!operation.isCompleted(), "Operation should never be completed before the first process.");
		if (operation.process(delta, e)) {
			index++;
		}
	}

	@Override
	public void rewind() {
		super.rewind();
		index = 0;
	}

	@Override
	public void reset() {
		super.reset();
		index = 0;
	}
}

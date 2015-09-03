package net.mostlyoriginal.api.operation.flow;

import com.artemis.Entity;
import net.mostlyoriginal.api.operation.common.Operation;
import net.mostlyoriginal.api.operation.common.OperationFlow;

/**
 * Fires operations serially, waiting for each to finish before starting the next.
 * <p/>
 * Preserves and calls in order.
 *
 * @author Daan van Yperen
 */
public class SerialOperation extends OperationFlow {

	@Override
	public boolean process(float delta, Entity e) {

		if (operations.size > 0) {
			final Operation operation = operations.first();

			if (operation.process(delta, e)) {
				operations.removeIndex(0);
				operation.release();
			}
		}
		return isFinished();
	}
}

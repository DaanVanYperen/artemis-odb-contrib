package net.mostlyoriginal.api.operation.flow;

import com.artemis.Entity;
import net.mostlyoriginal.api.operation.common.Operation;
import net.mostlyoriginal.api.operation.common.OperationFlow;

/**
 * Fires operations serially, waiting for each to finish before starting the next.
 *
 * Preserves and calls in order.
 *
 * @author Daan van Yperen
 */
public class SerialOperation extends OperationFlow {

	@Override
	public boolean act(float delta, Entity e) {

		for (int i = 0, s = operations.size; i < s; i++) {
			final Operation operation = operations.get(i);

			if ( operation.act(world.delta, e) ) {
				operations.removeIndex(i);
				operation.release();
				return isFinished();
			}
		}
		return isFinished();
	}
}

package net.mostlyoriginal.api.operation.flow;

import com.artemis.Entity;
import net.mostlyoriginal.api.operation.common.Operation;
import net.mostlyoriginal.api.operation.common.OperationFlow;

/**
 * Run operations in parallel.
 *
 * @author Daan van Yperen
 */
public class ParallelOperation extends OperationFlow {

	@Override
	public boolean act(float delta, Entity e) {

		for (int i = 0, s = operations.size; i < s; i++) {
			final Operation operation = operations.get(i);

			if ( operation.act(world.delta, e) ) {
				operations.removeIndex(i);
				operation.release();
				i--;
				if ( !e.isActive() ) return true;
			}
		}

		return isFinished();
	}
}

package net.mostlyoriginal.api.component;

import com.artemis.PooledComponent;
import net.mostlyoriginal.api.operation.common.Operation;
import net.mostlyoriginal.api.operation.flow.ParallelOperation;
import net.mostlyoriginal.api.system.SchedulerSystem;

/**
 * Schedules multiple operations.
 *
 * @author Daan van Yperen
 * @see SchedulerSystem
 */
public class Schedule extends PooledComponent {

	public ParallelOperation operation = new ParallelOperation();

	public Schedule() {
	}

	@Override
	protected void reset() {
		// reset root container but keep the instance.
		operation.reset();
	}

	public Schedule(Operation operation) {
		this.operation.add(operation);
	}
}

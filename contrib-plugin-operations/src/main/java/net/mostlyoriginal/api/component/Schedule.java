package net.mostlyoriginal.api.component;

import com.artemis.PooledComponent;
import com.artemis.annotations.Fluid;
import net.mostlyoriginal.api.operation.common.Operation;
import net.mostlyoriginal.api.operation.flow.ParallelOperation;
import net.mostlyoriginal.api.system.SchedulerSystem;

/**
 * Schedules multiple operations.
 *
 * @author Daan van Yperen
 * @see SchedulerSystem
 */
@Fluid(name = "script")
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

	public void add(Operation operation) {
		this.operation.add(operation);
	}
	public void set(Operation operation) {
		this.operation.add(operation);
	}
	public void set(Operation operationA, Operation operationB) {
		this.operation.add(operationA);
		this.operation.add(operationB);
	}
	public void set(Operation operationA, Operation operationB, Operation operationC) {
		this.operation.add(operationA);
		this.operation.add(operationB);
		this.operation.add(operationC);
	}
}

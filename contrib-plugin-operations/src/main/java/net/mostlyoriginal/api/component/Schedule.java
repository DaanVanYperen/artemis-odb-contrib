package net.mostlyoriginal.api.component;

import com.artemis.Component;
import net.mostlyoriginal.api.operation.common.Operation;
import net.mostlyoriginal.api.operation.flow.ParallelOperation;
import net.mostlyoriginal.api.system.SchedulerSystem;

/**
 * @author Daan van Yperen
 * @see SchedulerSystem
 */
public class Schedule extends Component {

	public ParallelOperation operation = new ParallelOperation();

	public Schedule() {
	}

	public Schedule(Operation operation) {
		this.operation.add(operation);
	}
}

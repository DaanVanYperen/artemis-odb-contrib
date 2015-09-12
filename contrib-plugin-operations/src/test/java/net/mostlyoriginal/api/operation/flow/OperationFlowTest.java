package net.mostlyoriginal.api.operation.flow;

import net.mostlyoriginal.api.operation.common.OperationFlow;
import net.mostlyoriginal.api.operation.common.TestOperation;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Daan van Yperen
 */
public abstract class OperationFlowTest extends OperationTest {

	protected OperationFlow operation;
	protected TestOperation[] op;
	protected void createOps(int... maxCalls) {
		int index=0;
		TestOperation[] operations = new TestOperation[maxCalls.length];
		for (int maxCall : maxCalls) {
			TestOperation testOperation = new TestOperation(maxCall);
			operations[index++] = testOperation;
			operation.add(testOperation);
		}
		op = operations;
	}

	private void createOps(TestOperation... operations) {
		for (TestOperation op : operations) {
			operation.add(op);
		}
		op = operations;
	}

	protected void processOperation() {
		operation.process(0, entity);
	}

	@Test
	public void ensure_not_finished_when_operations_remain() {
		createOps(1);
		Assert.assertFalse(operation.isCompleted());
	}

	@Test
	public void ensure_finished_when_no_operations_remain() {
		createOps(1);

		processOperation();

		Assert.assertTrue(operation.isCompleted());
	}

	@Test
	public void ensure_restart_causes_child_operation_restart() {
		createOps(1);
		processOperation();
		operation.rewind();
		Assert.assertFalse(op[0].isCompleted());
	}
}

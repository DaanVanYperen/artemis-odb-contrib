package net.mostlyoriginal.api.operation.flow;

import net.mostlyoriginal.api.operation.common.TestOperation;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Daan van Yperen
 */
public class RepeatOperationTest extends OperationTest {

	private RepeatOperation operation;

	@Test
	public void ensure_completes_when_invocations_reached() {

		operation = new RepeatOperation();
		operation.setup(new TestOperation(2), 3);

		processOperation(6);
		assertTrue(operation.isCompleted());
	}

	@Test
	public void ensure_not_completed_when_invocations_not_reached() {

		operation = new RepeatOperation();
		operation.setup(new TestOperation(2), 3);

		processOperation(5);
		assertTrue(!operation.isCompleted());
	}

	@Test
	public void ensure_rewind_rewinds_child() {

		operation = new RepeatOperation();
		TestOperation childOperation = new TestOperation(1);
		this.operation.setup(childOperation, 1);
		processOperation(1);

		assertTrue(childOperation.isCompleted());
		this.operation.rewind();
		assertFalse(childOperation.isCompleted());
	}

	@Test
	public void ensure_unlimited_is_unlimited() {

		operation = new RepeatOperation();
		this.operation.setup(new TestOperation(2));

		processOperation(999); // close enough

		assertFalse(operation.isCompleted());
	}

	protected void processOperation(int times) {
		for ( int i=0;i<times;i++) {
			operation.process(0, entity);
		}
	}
}
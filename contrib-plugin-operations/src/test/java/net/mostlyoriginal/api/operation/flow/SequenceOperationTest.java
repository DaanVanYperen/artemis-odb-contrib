package net.mostlyoriginal.api.operation.flow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Daan van Yperen
 */
public class SequenceOperationTest extends OperationFlowTest {

	@Before
	public void setup() {
		operation = new SequenceOperation();
	}

	@Test
	public void ensure_operations_are_executed_serially() {

		createOps(1, 1);

		processOperation();

		// first called, second skipped.
		Assert.assertEquals(1, op[0].calls);
		Assert.assertEquals(0, op[1].calls);

		processOperation();

		// nothing happens to first because finished, second called.
		Assert.assertEquals(1, op[0].calls);
		Assert.assertEquals(1, op[1].calls);
	}

	@Test
	public void completed_operations_are_flagged_completed() {
		createOps(1);
		processOperation();
		Assert.assertTrue(op[0].isCompleted());
	}

	@Test
	public void not_yet_completed_operations_are_not_flagged_completed() {
		createOps(2);
		processOperation();
		Assert.assertFalse(op[0].isCompleted());
	}


	@Test
	public void ensure_restart_resets_sequence_index() {
		createOps(1, 1, 1);
		processOperation(); // process op 0, index++

		processOperation(); // process op 1, index++

		operation.rewind(); // index reset to 0
		Assert.assertEquals(0, op[0].calls); // should be 0 again

		processOperation(); // process op 0
		Assert.assertEquals(1, op[0].calls);
	}

}
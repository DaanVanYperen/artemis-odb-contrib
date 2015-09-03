package net.mostlyoriginal.api.operation.flow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Daan van Yperen
 */
public class SerialOperationTest extends OperationFlowTest {

	@Before
	public void setup() {
		operation = new SerialOperation();
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
}
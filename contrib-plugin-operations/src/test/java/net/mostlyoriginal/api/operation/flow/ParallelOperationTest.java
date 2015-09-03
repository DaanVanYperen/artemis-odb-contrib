package net.mostlyoriginal.api.operation.flow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Daan van Yperen
 */
public class ParallelOperationTest extends OperationFlowTest {

	@Before
	public void setup() {
		operation = new ParallelOperation();
	}

	@Test
	public void ensure_operations_are_executed_in_parallel() {

		createOps(2, 2, 2);

		processOperation();

		Assert.assertEquals(1, op[0].calls);
		Assert.assertEquals(1, op[1].calls);
		Assert.assertEquals(1, op[2].calls);
	}

}
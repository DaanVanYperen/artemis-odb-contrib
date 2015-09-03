package net.mostlyoriginal.api.operation.common;

import net.mostlyoriginal.api.operation.act.DeleteFromWorldOperation;
import net.mostlyoriginal.api.operation.flow.SerialOperation;
import org.junit.Assert;
import org.junit.Test;

import static net.mostlyoriginal.api.operation.OperationFactory.*;

/**
 * @author Daan van Yperen
 */
public class OperationFactoryTest {

	@Test
	public void chain_should_assemble_serial_of_1() {
		OperationFlow operation = chain(
				new TestOperation()
		);
		Assert.assertEquals(1, operation.operations.size);
	}
	@Test
	public void chain_should_assemble_serial_of_2() {
		OperationFlow operation = chain(
				new TestOperation(),
				new TestOperation()
		);
		Assert.assertEquals(2, operation.operations.size);
	}
	@Test
	public void chain_should_assemble_serial_of_3() {
		OperationFlow operation = chain(
				new TestOperation(),
				new TestOperation(),
				new TestOperation()
		);
		Assert.assertEquals(3, operation.operations.size);
	}
	@Test
	public void chain_should_assemble_serial_of_4() {
		OperationFlow operation = chain(
				new TestOperation(),
				new TestOperation(),
				new TestOperation(),
				new TestOperation()
		);
		Assert.assertEquals(4, operation.operations.size);
	}
	
	@Test
	public void chain_should_assemble_serial_of_5() {
		SerialOperation operation = chain(
				new TestOperation(),
				new TestOperation(),
				new TestOperation(),
				new TestOperation(),
				new TestOperation()
		);
		Assert.assertEquals(5, operation.operations.size);
	}

	@Test
	public void parallel_should_assemble_parallel_of_1() {
		OperationFlow operation = parallel(
				new TestOperation()
		);
		Assert.assertEquals(1, operation.operations.size);
	}
	@Test
	public void parallel_should_assemble_parallel_of_2() {
		OperationFlow operation = parallel(
				new TestOperation(),
				new TestOperation()
		);
		Assert.assertEquals(2, operation.operations.size);
	}
	@Test
	public void parallel_should_assemble_parallel_of_3() {
		OperationFlow operation = parallel(
				new TestOperation(),
				new TestOperation(),
				new TestOperation()
		);
		Assert.assertEquals(3, operation.operations.size);
	}
	@Test
	public void parallel_should_assemble_parallel_of_4() {
		OperationFlow operation = parallel(
				new TestOperation(),
				new TestOperation(),
				new TestOperation(),
				new TestOperation()
		);
		Assert.assertEquals(4, operation.operations.size);
	}
	@Test
	public void parallel_should_assemble_parallel_of_5() {
		OperationFlow operation = parallel(
				new TestOperation(),
				new TestOperation(),
				new TestOperation(),
				new TestOperation(),
				new TestOperation()
		);
		Assert.assertEquals(5, operation.operations.size);
	}

	@Test
	public void nesting_should_assemble_nested_operation() {
		OperationFlow operation =
				parallel(
						chain(
								deleteFromWorld()
						));

		Assert.assertTrue(((OperationFlow)operation.operations.get(0)).operations.get(0) instanceof DeleteFromWorldOperation);

	}
}

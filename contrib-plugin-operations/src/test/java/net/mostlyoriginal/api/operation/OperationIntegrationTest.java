package net.mostlyoriginal.api.operation;

import com.artemis.Component;
import net.mostlyoriginal.api.operation.basic.LegacyAddOperation;
import net.mostlyoriginal.api.operation.common.Operation;
import net.mostlyoriginal.api.operation.common.TestOperation;
import net.mostlyoriginal.api.operation.flow.OperationTest;
import net.mostlyoriginal.api.operation.flow.ParallelOperation;
import net.mostlyoriginal.api.operation.flow.RepeatOperation;
import net.mostlyoriginal.api.operation.flow.SequenceOperation;
import net.mostlyoriginal.api.operation.temporal.DelayOperation;
import org.junit.Assert;
import org.junit.Test;

import static net.mostlyoriginal.api.operation.OperationFactory.*;

/**
 * Some basic integration tests for operations.
 *
 * @author Daan van Yperen
 */
public class OperationIntegrationTest extends OperationTest {


	public static class TestComponent extends Component {};

	@Test
	public void ensure_operations_chain_released() {

		DelayOperation delayOperation = delay(1);
		Operation deleteFromWorldOperation = deleteFromWorld();
		LegacyAddOperation legacyAddOperation = add(new TestComponent());

		SequenceOperation sequenceOperation = sequence(delayOperation, deleteFromWorldOperation, legacyAddOperation);
		ParallelOperation parallelOperation = parallel(sequenceOperation);

		// set all completed as an easy test to see if reset.
		delayOperation.setCompleted(true);
		deleteFromWorldOperation.setCompleted(true);
		legacyAddOperation.setCompleted(true);
		sequenceOperation.setCompleted(true);
		parallelOperation.setCompleted(true);

		parallelOperation.reset();

		Assert.assertFalse(delayOperation.isCompleted());
		Assert.assertFalse(deleteFromWorldOperation.isCompleted());
		Assert.assertFalse(legacyAddOperation.isCompleted());
		Assert.assertFalse(sequenceOperation.isCompleted());
		Assert.assertFalse(parallelOperation.isCompleted());
	}

	@Test
	public void ensure_nested_repeats_work() {
		RepeatOperation operation = repeat(99,
				repeat(4,
						new TestOperation(1)
				));

		process(operation,12);

		Assert.assertEquals(3, operation.getRepetition());
	}

	@Test
	public void ensure_repeat_repeats_completed_sequence() {

		TestOperation o1 = new TestOperation(1);
		TestOperation o2 = new TestOperation(1);
		TestOperation o3 = new TestOperation(1);

		RepeatOperation repeat = repeat(2,
				sequence(
						o1,
						o2,
						o3
				));


		process(repeat, 4); // will run o1,o2,o3 and then o1 again.

		Assert.assertEquals(1, repeat.getRepetition());
		Assert.assertEquals(1, o1.calls );
		Assert.assertEquals(0, o2.calls );
	}

	@Test
	public void ensure_repeat_waits_for_sequence_to_complete() {

		TestOperation o1 = new TestOperation(1);
		TestOperation o2 = new TestOperation(1);
		TestOperation o3 = new TestOperation(1);

		RepeatOperation repeat = repeat(2,
				sequence(
						o1,
						o2,
						o3
				));


		process(repeat, 2);

		Assert.assertEquals(0, repeat.getRepetition());
		Assert.assertEquals(1, o1.calls);
		Assert.assertEquals(1, o2.calls);
	}

	@Test
	public void ensure_repeat_does_not_repeat_unfinished_parallel_sequence() {

		TestOperation o1 = new TestOperation(1);
		TestOperation o2 = new TestOperation(10); // wants 10 invocations.
		TestOperation o3 = new TestOperation(1);

		RepeatOperation repeat = repeat(2,
				parallel(
						o1,
						o2,
						o3
				));


		process(repeat, 9); // should not be enough to repeat invocation.

		Assert.assertEquals(0, repeat.getRepetition());
	}

	@Test
	public void ensure_repeat_does_repeats_finished_parallel_sequence() {

		TestOperation o1 = new TestOperation(1);
		TestOperation o2 = new TestOperation(10); // wants 10 invocations.
		TestOperation o3 = new TestOperation(1);

		RepeatOperation repeat = repeat(2,
				parallel(
						o1,
						o2,
						o3
				));


		process(repeat, 10); // enough to begin repetition.

		Assert.assertEquals(1, repeat.getRepetition());
	}


	protected void process(Operation repeat, int count) {
		for (int i = 0; i < count; i++) {
			repeat.process(0, entity);
		}
	}

}

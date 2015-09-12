package net.mostlyoriginal.api.operation.flow;

import com.artemis.Entity;
import net.mostlyoriginal.api.operation.common.Operation;
import net.mostlyoriginal.api.utils.Preconditions;

/**
 * Repeat nested operation one or more times.
 *
 * @author Daan van Yperen
 */
public class RepeatOperation extends Operation {

	public static final int UNLIMITED = -1;
	private Operation operation;
	private int repetition;
	private int desiredRepetitions;

	/**
	 * Run operation for specific number of invocations.
	 *
	 * @param operation Operation to wrap.
	 * @param desiredRepetitions invocations to run the operation, -1 for unlimited.
	 */
	public void setup( Operation operation, int desiredRepetitions )
	{
		this.operation = Preconditions.checkNotNull(operation);
		this.desiredRepetitions = desiredRepetitions;
	}

	/**
	 * Run operation for unlimited times.
	 *
	 * @param operation Operation to wrap.
	 */
	public void setup( Operation operation )
	{
		setup(operation, UNLIMITED);
	}

	@Override
	public boolean process(float delta, Entity e) {
		if (!completed) {
			if ( operation.process(delta,e) ) {
				completed = (desiredRepetitions != UNLIMITED) && (++repetition >= desiredRepetitions);
				if ( !completed ) {
					operation.rewind();
				}
			}
		}
		return completed;
	}

	@Override
	public void rewind() {
		super.rewind();
		repetition = 0;
		operation.rewind();
	}

	@Override
	public void reset() {
		super.reset();
		// will be called immediately upon pool release.

		repetition = 0;
		desiredRepetitions = 0;

		if (operation != null) {
			operation.release();
			operation = null;
		}
	}

	public int getRepetition() {
		return repetition;
	}
}

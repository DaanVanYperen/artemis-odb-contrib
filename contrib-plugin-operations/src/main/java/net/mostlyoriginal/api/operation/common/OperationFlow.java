package net.mostlyoriginal.api.operation.common;

import com.badlogic.gdx.utils.Array;

/**
 * Operation that handles flow of child operations.
 *
 * @author Daan van Yperen
 */
public abstract class OperationFlow extends Operation {

	public Array<Operation> operations = new Array<>(true, 4);

	protected void releaseChildren() {
		for (int i = 0, s = operations.size; i < s; i++) {
			operations.get(i).release();
		}
		operations.clear();
	}

	@Override
	public void reset() {
		// will be called immediately upon pool release.
		releaseChildren();
	}

	/** Add operation to end of flow. */
	public void add(Operation value) {
		operations.add(value);
	}

	/**
	 * @return {@code true} if no operations remaining, {@code false} if otherwise.
	 */
	public boolean isFinished() {
		return operations.size == 0;
	}
}

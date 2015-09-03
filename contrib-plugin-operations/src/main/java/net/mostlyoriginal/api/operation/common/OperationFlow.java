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

	public void addAll(Operation o1)
	{
		operations.ensureCapacity(5);
		operations.add(o1);
	}

	public void addAll(Operation o1, Operation o2)
	{
		operations.ensureCapacity(5);
		operations.add(o1);
		operations.add(o2);
	}

	public void addAll(Operation o1, Operation o2, Operation o3)
	{
		operations.ensureCapacity(5);
		operations.add(o1);
		operations.add(o2);
		operations.add(o3);
	}

	public void addAll(Operation o1, Operation o2, Operation o3, Operation o4)
	{
		operations.ensureCapacity(5);
		operations.add(o1);
		operations.add(o2);
		operations.add(o3);
		operations.add(o4);
	}

	public void addAll(Operation o1, Operation o2, Operation o3, Operation o4, Operation o5)
	{
		operations.ensureCapacity(5);
		operations.add(o1);
		operations.add(o2);
		operations.add(o3);
		operations.add(o4);
		operations.add(o5);
	}
}

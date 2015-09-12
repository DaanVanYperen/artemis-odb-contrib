package net.mostlyoriginal.api.operation.common;

import com.badlogic.gdx.utils.Array;
import net.mostlyoriginal.api.utils.Preconditions;

/**
 * Operation that handles flow of child operations.
 *
 * @author Daan van Yperen
 */
public abstract class OperationFlow extends Operation {

	public Array<Operation> operations = new Array<>(true, 4);

	public void clear() {
		for (int i = 0, s = operations.size; i < s; i++) {
			operations.get(i).release();
		}
		operations.clear();
	}

	@Override
	public void rewind() {
		super.rewind();
		for (int i = 0, s = operations.size; i < s; i++) {
			operations.get(i).rewind();
		}
	}

	@Override
	public void reset() {
		super.reset();
		// will be called immediately upon pool release.
		clear();
	}

	/** Add operation to end of flow. */
	public void add(Operation value) {
		completed=false;
		Preconditions.checkNotNull(value);
		operations.add(value);
	}

	public void addAll(Operation o1)
	{
		operations.ensureCapacity(1);
		add(o1);
	}

	public void addAll(Operation o1, Operation o2)
	{
		operations.ensureCapacity(2);
		add(o1);
		add(o2);
	}

	public void addAll(Operation o1, Operation o2, Operation o3)
	{
		operations.ensureCapacity(3);
		add(o1);
		add(o2);
		add(o3);
	}

	public void addAll(Operation o1, Operation o2, Operation o3, Operation o4)
	{
		operations.ensureCapacity(4);
		add(o1);
		add(o2);
		add(o3);
		add(o4);
	}

	public void addAll(Operation o1, Operation o2, Operation o3, Operation o4, Operation o5)
	{
		operations.ensureCapacity(5);
		add(o1);
		add(o2);
		add(o3);
		add(o4);
		add(o5);
	}
}

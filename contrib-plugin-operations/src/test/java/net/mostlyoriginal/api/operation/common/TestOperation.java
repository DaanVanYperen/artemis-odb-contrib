package net.mostlyoriginal.api.operation.common;

import com.artemis.Entity;

/**
 * @author Daan van Yperen
 */
public class TestOperation extends Operation {
	public int calls = 0;
	public int maxCalls = 9999;
	public int resets = 0;

	public TestOperation() {
	}

	public TestOperation(int maxCalls) {
		this.maxCalls = maxCalls;
	}

	@Override
	public boolean process(float delta, Entity e) {
		if ( isCompleted() ) return true;
		calls++;
		completed = calls >= maxCalls;
		return completed;
	}

	@Override
	public void rewind() {
		super.rewind();
		calls=0;
	}

	@Override
	public void reset() {
		super.reset();
		resets++; calls=0;
	}
}

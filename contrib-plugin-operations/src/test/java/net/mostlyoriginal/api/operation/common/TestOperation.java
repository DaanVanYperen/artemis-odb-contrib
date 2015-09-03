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
		calls++;
		return calls >= maxCalls;
	}

	@Override
	public void reset() {
		resets++;
	}
}

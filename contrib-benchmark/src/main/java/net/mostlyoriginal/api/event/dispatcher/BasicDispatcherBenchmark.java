package net.mostlyoriginal.api.event.dispatcher;

import net.mostlyoriginal.api.event.common.EventDispatchStrategy;

/**
 * Test basic dispatcher.
 *
 * @author DaanVanYperen
 */
public class BasicDispatcherBenchmark extends DispatcherBenchmark {

	protected EventDispatchStrategy instanceDispatcher() {
		return new BasicEventDispatcher();
	}
}

package net.mostlyoriginal.api.event.dispatcher;

import net.mostlyoriginal.api.event.common.EventDispatchStrategy;

/**
 * Test basic dispatcher.
 *
 * @author DaanVanYperen
 */
public class PollingPooledDispatcherBenchmark extends ClassBasedDispatcherBenchmark {

	protected EventDispatchStrategy instanceDispatcher() {
		return new PollingPooledEventDispatcher();
	}
}

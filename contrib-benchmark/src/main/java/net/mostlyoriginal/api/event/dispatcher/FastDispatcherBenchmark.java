package net.mostlyoriginal.api.event.dispatcher;

import net.mostlyoriginal.api.event.common.EventDispatchStrategy;

/**
 * Test basic dispatcher.
 *
 * @author DaanVanYperen
 */
public class FastDispatcherBenchmark extends DispatcherBenchmark {

	protected EventDispatchStrategy instanceDispatcher() {
		return new FastEventDispatcher();
	}
}

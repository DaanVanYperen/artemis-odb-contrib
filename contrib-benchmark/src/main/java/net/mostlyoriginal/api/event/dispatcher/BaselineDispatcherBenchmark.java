package net.mostlyoriginal.api.event.dispatcher;

import net.mostlyoriginal.api.event.common.Event;
import net.mostlyoriginal.api.event.common.EventDispatchStrategy;
import net.mostlyoriginal.api.event.common.EventListener;

/**
 * Test basic dispatcher.
 *
 * @author DaanVanYperen
 */
public class BaselineDispatcherBenchmark extends DispatcherBenchmark {

	protected EventDispatchStrategy instanceDispatcher() {
		return new EmptyEventDispatcher();
	}

	private class EmptyEventDispatcher implements EventDispatchStrategy {
		@Override
		public void register(EventListener listener) {
		}

		@Override
		public void dispatch(Event event) {
		}
	}
}

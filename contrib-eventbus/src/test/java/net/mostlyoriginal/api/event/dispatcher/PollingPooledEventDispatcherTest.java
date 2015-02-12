package net.mostlyoriginal.api.event.dispatcher;

import net.mostlyoriginal.api.event.common.Event;
import net.mostlyoriginal.api.event.common.EventDispatchStrategy;
import org.junit.Test;

public class PollingPooledEventDispatcherTest extends AbstractEventDispatcherTest {
	@Override
	protected EventDispatchStrategy createDispatcherInstance() {
		return new PollingPooledEventDispatcher();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void Dispatch_ShouldNotDispatchInstancedEvents()
	{
		dispatcher.dispatch(new BaseEvent());
	}

	/** Dispatch wrapper. */
	@Override
	protected void dispatch(Event event) {
		try {
			dispatcher.dispatch(event.getClass());
			// this dispatcher processes after a world tick.
			dispatcher.process();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}

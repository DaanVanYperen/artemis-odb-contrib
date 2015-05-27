package net.mostlyoriginal.api.event.dispatcher;

import org.junit.Test;

/**
 * Test basic event dispatcher.
 *
 * Created by Daan on 14-9-2014.
 */
public class BasicEventDispatcherTest extends AbstractEventDispatcherTest {
	protected BasicEventDispatcher createDispatcherInstance() {
		return new BasicEventDispatcher();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void Dispatch_ShouldNotDispatchInstancedEvents()
	{
		dispatcher.dispatch(BaseEvent.class);
	}
}

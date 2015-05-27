package net.mostlyoriginal.api.event.dispatcher;

import net.mostlyoriginal.api.event.common.EventDispatchStrategy;
import org.junit.Test;

/**
 * Created by Daan on 14-9-2014.
 */
public class FastEventDispatcherTest extends AbstractEventDispatcherTest {
	@Override
	protected EventDispatchStrategy createDispatcherInstance() {
		return new FastEventDispatcher();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void Dispatch_ShouldNotDispatchClassEvents()
	{
		dispatcher.dispatch(BaseEvent.class);
	}
}

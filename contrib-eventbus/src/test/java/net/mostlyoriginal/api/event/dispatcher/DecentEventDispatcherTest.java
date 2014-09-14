package net.mostlyoriginal.api.event.dispatcher;

import net.mostlyoriginal.api.event.common.EventDispatchStrategy;

/**
 * Created by Daan on 14-9-2014.
 */
public class DecentEventDispatcherTest extends AbstractEventDispatcherTest {
	@Override
	protected EventDispatchStrategy createDispatcherInstance() {
		return new DecentEventDispatcher();
	}
}

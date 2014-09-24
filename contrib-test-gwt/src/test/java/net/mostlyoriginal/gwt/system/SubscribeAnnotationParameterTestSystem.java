package net.mostlyoriginal.gwt.system;

import net.mostlyoriginal.api.event.common.Subscribe;

/**
 * @author Daan van Yperen
 */
public class SubscribeAnnotationParameterTestSystem {
        @Subscribe(priority = 5, ignoreCancelledEvents = true)
        public void testListener(ExtendedBasicTestEvent event) { }
}

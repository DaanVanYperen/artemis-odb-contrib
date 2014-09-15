package net.mostlyoriginal.gwt.system;

import net.mostlyoriginal.api.event.common.Subscribe;

/**
 * @author Daan van Yperen
 */
public class SubscribeAnnotationTestSystem {
        public int count;
        @Subscribe
        public void testListener(ExtendedBasicTestEvent event) { }
        @Subscribe
        public void testListener2(BasicTestEvent event) { }
        public void notARegisteredListener(BasicTestEvent event) { }
}

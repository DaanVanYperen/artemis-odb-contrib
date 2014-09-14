package net.mostlyoriginal.api.event.dispatcher;

import com.artemis.World;
import net.mostlyoriginal.api.event.common.EventDispatchStrategy;
import net.mostlyoriginal.api.event.common.EventManager;
import net.mostlyoriginal.api.event.common.SubscribeAnnotationFinder;
import org.openjdk.jmh.annotations.*;

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

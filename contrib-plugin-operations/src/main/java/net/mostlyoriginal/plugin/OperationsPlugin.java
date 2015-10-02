package net.mostlyoriginal.plugin;

import com.artemis.ArtemisPlugin;
import com.artemis.WorldConfigurationBuilder;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.ExtendedComponentMapperPlugin;
import net.mostlyoriginal.api.system.SchedulerSystem;

/**
 * Scheduled operations on entities and components.
 *
 * @see net.mostlyoriginal.api.operation.common.Operation hierarchy.
 * @see net.mostlyoriginal.api.component.Schedule
 * @see SchedulerSystem
 * @author Daan van Yperen
 */
public class OperationsPlugin implements ArtemisPlugin {

	@Override
	public void setup(WorldConfigurationBuilder b) {
		b.dependsOn(ExtendedComponentMapperPlugin.class);
		b.dependsOn(WorldConfigurationBuilder.Priority.OPERATIONS, SchedulerSystem.class);
	}
}
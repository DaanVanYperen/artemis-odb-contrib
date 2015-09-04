package net.mostlyoriginal.plugin;

import net.mostlyoriginal.api.plugin.common.ArtemisPlugin;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.ExtendedComponentMapperPlugin;
import net.mostlyoriginal.api.system.SchedulerSystem;
import net.mostlyoriginal.api.utils.builder.WorldConfigurationBuilder;

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
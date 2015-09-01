package net.mostlyoriginal.plugin;

import net.mostlyoriginal.api.ProfilerInvocationStrategy;
import net.mostlyoriginal.api.plugin.common.ArtemisPlugin;
import net.mostlyoriginal.api.utils.builder.WorldConfigurationBuilder;

/**
 * Artemis system profiler.
 *
 * @author piotr-j
 * @author Daan van Yperen
 */
public class ProfilerPlugin implements ArtemisPlugin {

	@Override
	public void setup(WorldConfigurationBuilder b) {
		b.register(new ProfilerInvocationStrategy());
		b.dependsOn(WorldConfigurationBuilder.Priority.LOWEST + 1000,ProfilerSystem.class);
	}
}
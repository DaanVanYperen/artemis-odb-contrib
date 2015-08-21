package net.mostlyoriginal.api.utils.builder.common;

import net.mostlyoriginal.api.plugin.common.ArtemisPlugin;
import net.mostlyoriginal.api.utils.builder.WorldConfigurationBuilder;

/**
 * @author Daan van Yperen
 */
public class TestPluginBDependentOnA implements ArtemisPlugin {
	@Override
	public void setup(WorldConfigurationBuilder b) {
		b.dependsOn(TestPluginA.class);
	}
}

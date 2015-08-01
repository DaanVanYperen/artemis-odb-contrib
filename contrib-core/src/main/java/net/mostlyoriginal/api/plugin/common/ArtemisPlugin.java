package net.mostlyoriginal.api.plugin.common;

import net.mostlyoriginal.api.utils.builder.WorldConfigurationBuilder;

/**
 * Plugin for artemis-odb.
 *
 * @author Daan van Yperen
 */
public interface ArtemisPlugin {

	/**
	 * Register your plugin.
	 *
	 * Set up all your dependencies:
	 * - systems
	 * - managers
	 * - field resolvers
	 * - other plugins
	 *
	 * @param b builder to register your dependencies with.
	 */
	void setup( WorldConfigurationBuilder b );
}

package net.mostlyoriginal.api.plugin.extendedcomponentmapper;

import com.artemis.ArtemisPlugin;
import com.artemis.WorldConfigurationBuilder;

/**
 * Plugin that adds support for extended component mappers.
 * <p/>
 * Register with {@see WorldConfigurationBuilder}.
 *
 * @author Daan van Yperen
 * @see M
 */
public class ExtendedComponentMapperPlugin implements ArtemisPlugin {

	@Override
	public void setup(WorldConfigurationBuilder b) {
		b.register(new ExtendedComponentMapperFieldResolver());
		b.dependsOn(ExtendedComponentMapperManager.class);
	}
}

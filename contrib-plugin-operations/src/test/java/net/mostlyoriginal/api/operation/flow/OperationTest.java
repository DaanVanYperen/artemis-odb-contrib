package net.mostlyoriginal.api.operation.flow;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.ExtendedComponentMapperPlugin;
import org.junit.Before;

/**
 * @author Daan van Yperen
 */
public class OperationTest{

	protected Entity entity;
	protected World world;

	@Before
	public void setupWorld() {
		world = new World(new WorldConfigurationBuilder().dependsOn(ExtendedComponentMapperPlugin.class).build());
		entity = world.createEntity();
		world.process();
	}

}

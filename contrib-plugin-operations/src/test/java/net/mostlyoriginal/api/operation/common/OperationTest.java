package net.mostlyoriginal.api.operation.common;

import com.artemis.Entity;
import com.artemis.World;
import org.junit.Before;

/**
 * @author Daan van Yperen
 */
public class OperationTest {

	protected World world;
	protected Entity entity;

	@Before
	public void baseSetup() {
		world = new World();
		entity = world.createEntity();
		world.process();
	}

}

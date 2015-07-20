package net.mostlyoriginal.api.utils.builder;

import com.artemis.BaseSystem;
import com.artemis.Manager;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import org.junit.Assert;
import org.junit.Test;

public class WorldConfigurationBuilderTest {

	@Test
	public void should_create_managers_on_build() {
		Manager manager1 = new TestManager();
		Manager manager2 = new TestManager();
		Manager manager3 = new TestManager();

		WorldConfiguration config = new WorldConfigurationBuilder()
				.with(manager1, manager2).build();

		World world = new World(config);

		Assert.assertTrue(world.getManagers().contains(manager1));
		Assert.assertTrue(world.getManagers().contains(manager2));
	}

	@Test
	public void should_create_systems_in_order() {
		BaseSystem system1 = new TestEntitySystem();
		BaseSystem system2 = new TestEntitySystem();
		BaseSystem system3 = new TestEntitySystem();

		World world = new World(new WorldConfigurationBuilder()
				.with(system1, system2)
				.with(system3).build());

		Assert.assertEquals(system1, world.getSystems().get(0));
		Assert.assertEquals(system2, world.getSystems().get(1));
		Assert.assertEquals(system3, world.getSystems().get(2));
	}

	@Test
	public void should_add_systems_as_active_by_default() {
		World world = new World(new WorldConfigurationBuilder()
				.with(new TestEntitySystem()).build());

		Assert.assertFalse(world.getSystems().get(0).isPassive());
	}

	@Test
	public void should_add_passive_systems_as_passive() {
		World world =  new World(new WorldConfigurationBuilder()
				.withPassive(new TestEntitySystem()).build());

		Assert.assertTrue(world.getSystems().get(0).isPassive());
	}

	@Test
	public void should_not_carry_over_old_systems_to_new_world() {
		WorldConfigurationBuilder builder = new WorldConfigurationBuilder();
		World world1 = new World(builder.withPassive(new TestEntitySystem()).build());
		World world2 = new World(builder.build());
		Assert.assertEquals(0, world2.getSystems().size());
	}

	private static final class TestEntitySystem extends BaseSystem {
		@Override
		protected void processSystem() {
		}
	}
	private static final class TestManager extends Manager{}
}
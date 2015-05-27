package net.mostlyoriginal.api.utils.builder;

import com.artemis.BaseSystem;
import com.artemis.Manager;
import com.artemis.World;
import com.artemis.systems.VoidEntitySystem;
import org.junit.Assert;
import org.junit.Test;

public class WorldBuilderTest {

	@Test
	public void should_create_managers_on_build() {
		Manager manager1 = new TestManager();
		Manager manager2 = new TestManager();
		Manager manager3 = new TestManager();

		World world = new WorldBuilder()
				.with(manager1, manager2).build();

		Assert.assertTrue(world.getManagers().contains(manager1));
		Assert.assertTrue(world.getManagers().contains(manager2));
	}

	@Test
	public void should_create_systems_in_order() {
		BaseSystem system1 = new TestEntitySystem();
		BaseSystem system2 = new TestEntitySystem();
		BaseSystem system3 = new TestEntitySystem();

		World world = new WorldBuilder()
				.with(system1, system2)
				.with(system3).initialize();

		Assert.assertEquals(system1, world.getSystems().get(0));
		Assert.assertEquals(system2, world.getSystems().get(1));
		Assert.assertEquals(system3, world.getSystems().get(2));
	}

	@Test
	public void should_add_systems_as_active_by_default() {
		World world = new WorldBuilder()
				.with(new TestEntitySystem()).initialize();

		Assert.assertFalse(world.getSystems().get(0).isPassive());
	}

	@Test
	public void should_add_passive_systems_as_passive() {
		World world = new WorldBuilder()
				.withPassive(new TestEntitySystem()).initialize();

		Assert.assertTrue(world.getSystems().get(0).isPassive());
	}

	@Test
	public void should_not_carry_over_old_systems_to_new_world() {
		WorldBuilder builder = new WorldBuilder();
		World world1 = builder.withPassive(new TestEntitySystem()).initialize();
		World world2 = builder.initialize();
		Assert.assertEquals(0, world2.getSystems().size());
	}

	private static final class TestEntitySystem extends VoidEntitySystem {
		@Override
		protected void processSystem() {
		}
	}
	private static final class TestManager extends Manager{}
}
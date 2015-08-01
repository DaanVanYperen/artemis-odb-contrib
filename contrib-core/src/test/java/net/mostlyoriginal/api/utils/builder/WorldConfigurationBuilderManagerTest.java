package net.mostlyoriginal.api.utils.builder;

import com.artemis.Manager;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import net.mostlyoriginal.api.plugin.common.ArtemisPlugin;
import net.mostlyoriginal.api.utils.builder.common.TestEntityManagerA;
import net.mostlyoriginal.api.utils.builder.common.TestEntityManagerB;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Daan van Yperen
 */
public class WorldConfigurationBuilderManagerTest {

	private WorldConfigurationBuilder builder;

	@Before
	public void setUp() throws Exception {
		builder = new WorldConfigurationBuilder();
	}

	@Test(expected = WorldConfigurationException.class)
	public void should_refuse_duplicate_managers() {
		builder.with(new TestEntityManagerA(), new TestEntityManagerB(), new TestEntityManagerA()).build();
	}

	@Test
	public void should_support_multiple_plugins_with_same_manager_dependencies() {
		class SharedDependencyPlugin implements ArtemisPlugin {
			@Override
			public void setup(WorldConfigurationBuilder b) {
				builder.dependsOn(TestEntityManagerA.class);
			}
		}
		class SharedDependencyPluginB extends SharedDependencyPlugin {}

		final World world = new World(builder.with(new SharedDependencyPlugin(), new SharedDependencyPluginB()).build());
		Assert.assertNotNull(world.getManager(TestEntityManagerA.class));
	}

	@Test
	public void should_create_managers_on_build() {
		Manager manager1 = new TestEntityManagerA();
		Manager manager2 = new TestEntityManagerB();

		WorldConfiguration config = new WorldConfigurationBuilder()
				.with(manager1, manager2).build();

		World world = new World(config);

		Assert.assertTrue(world.getManagers().contains(manager1));
		Assert.assertTrue(world.getManagers().contains(manager2));
	}

}

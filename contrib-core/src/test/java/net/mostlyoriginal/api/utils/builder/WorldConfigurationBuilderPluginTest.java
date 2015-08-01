package net.mostlyoriginal.api.utils.builder;

import net.mostlyoriginal.api.plugin.common.ArtemisPlugin;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Daan van Yperen
 */
public class WorldConfigurationBuilderPluginTest {

	private WorldConfigurationBuilder builder;
	private ArtemisPlugin plugin;

	@Before
	public void setUp() throws Exception {
		builder = new WorldConfigurationBuilder();
		plugin = mock(ArtemisPlugin.class);
	}


	@Test
	public void should_register_plugins() {
		builder.with(plugin).build();
		verify(plugin).setup(any(WorldConfigurationBuilder.class));
	}


	@Test
	public void should_register_last_minute_nested_plugins() {

		final ArtemisPlugin parentPlugin = new ArtemisPlugin() {
			@Override
			public void setup(WorldConfigurationBuilder b) {
				b.with(plugin);
			}
		};

		builder.with(parentPlugin).build();

		verify(plugin).setup(any(WorldConfigurationBuilder.class));
	}

	@Test
	public void should_ignore_double_plugins() {

		final ArtemisPlugin parentPlugin = new ArtemisPlugin() {
			@Override
			public void setup(WorldConfigurationBuilder b) {
				b.with(plugin, plugin);
				b.with(plugin);
			}
		};

		builder.with(parentPlugin).build();

		verify(plugin, atMost(1)).setup(any(WorldConfigurationBuilder.class));
	}

	@Test
	public void should_avoid_cyclic_dependencies() {
		final ArtemisPlugin parentPlugin = new ArtemisPlugin() {
			@Override
			public void setup(WorldConfigurationBuilder b) {
				b.with(this);
			}
		};
		builder.with(parentPlugin).build();
		// will get stuck in loop if failed.
	}
}

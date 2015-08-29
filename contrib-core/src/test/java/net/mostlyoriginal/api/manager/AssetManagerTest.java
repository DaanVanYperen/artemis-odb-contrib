package net.mostlyoriginal.api.manager;

import com.artemis.Entity;
import com.artemis.Manager;
import com.artemis.World;
import com.artemis.annotations.Wire;
import net.mostlyoriginal.api.common.Sound;
import net.mostlyoriginal.api.common.SoundReference;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.ExtendedComponentMapperPlugin;
import net.mostlyoriginal.api.utils.builder.WorldConfigurationBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Daan van Yperen
 */
public class AssetManagerTest {

	protected World createWorld(Manager manager) {
		return new World(new WorldConfigurationBuilder()
				.with(manager)
				.with(new ExtendedComponentMapperPlugin())
				.build());
	}


	@Test
	public void new_asset_should_setup_reference() {

		@Wire(injectInherited = true)
		class TestManager extends AssetManager<Sound, SoundReference> {

			public TestManager() {
				super(Sound.class, SoundReference.class);
			}

			@Override
			protected void setup(Entity entity, Sound sound, SoundReference soundReference) {
				Assert.assertNotNull(sound);
				Assert.assertNotNull(soundReference);
			}
		}

		World world = createWorld(new TestManager());
		world.createEntity().edit().create(Sound.class);
		world.process();
	}

	@Test
	public void existing_asset_should_do_nothing() {
		@Wire(injectInherited = true)
		class TestManager extends AssetManager<Sound, SoundReference> {
			int count=0;

			public TestManager() {
				super(Sound.class, SoundReference.class);
			}

			@Override
			protected void setup(Entity entity, Sound sound, SoundReference soundReference) {
				count++;
			}
		}

		TestManager manager = new TestManager();
		World world = createWorld(manager);
		world.createEntity().edit().create(Sound.class);
		world.process();
		world.process(); // second process should not cause changes.

		Assert.assertEquals(manager.count, 1);
	}

	@Test
	public void resolved_asset_removed_reference_causes_reload() {
		@Wire(injectInherited = true)
		class TestManager extends AssetManager<Sound, SoundReference> {
			int count=0;

			public TestManager() {
				super(Sound.class, SoundReference.class);
			}

			@Override
			protected void setup(Entity entity, Sound sound, SoundReference soundReference) {
				count++;
			}
		}

		TestManager manager = new TestManager();
		World world = createWorld(manager);
		Entity entity = world.createEntity();
		entity.edit().create(Sound.class);
		world.process();
		entity.edit().remove(SoundReference.class);
		world.process();

		Assert.assertEquals(manager.count, 2);
	}

	@Test
	public void resolved_asset_removed_metadata_causes_removedreference() {
		@Wire(injectInherited = true)
		class TestManager extends AssetManager<Sound, SoundReference> {
			public TestManager() {
				super(Sound.class, SoundReference.class);
			}
			@Override
			protected void setup(Entity entity, Sound sound, SoundReference soundReference) {
			}
		}

		TestManager manager = new TestManager();
		World world = createWorld(manager);
		Entity entity = world.createEntity();
		entity.edit().create(Sound.class);
		world.process();
		entity.edit().remove(Sound.class);
		world.process();

		Assert.assertNull(entity.getComponent(SoundReference.class));
	}
}
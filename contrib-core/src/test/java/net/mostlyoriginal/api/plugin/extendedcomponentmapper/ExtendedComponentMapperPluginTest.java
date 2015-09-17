package net.mostlyoriginal.api.plugin.extendedcomponentmapper;

import com.artemis.*;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Daan van Yperen
 */
public class ExtendedComponentMapperPluginTest {

	@Wire
	public class BasicSystem extends EntityProcessingSystem {

		public BasicSystem() {
			super(Aspect.all(TestMarker.class));
		}

		protected M<Pos> mPos;

		@Override
		protected void process(Entity e) {
		}
	}

	@Test
	public void test_injection_of_extended_component_mapper() {
		final BasicSystem system = new BasicSystem();
		new World(new WorldConfigurationBuilder()
				.with(system)
				.with(new ExtendedComponentMapperPlugin())
				.build());

		Assert.assertNotNull(system.mPos);
		Assert.assertEquals(system.mPos.getClass(), M.class);
	}


	@Test
	public void create_if_missing_should_create_new_component() {

		@Wire(injectInherited = true)
		class TestSystemA extends BasicSystem {
			@Override
			protected void process(Entity e) {
				Pos c = mPos.create(e);
				Assert.assertNotNull(c);
			}
		}

		createAndProcessWorld(new TestSystemA());
	}

	@Test
	public void create_if_exists_should_recycle_existing_component() {

		@Wire(injectInherited = true)
		class TestSystem extends BasicSystem {
			@Override
			protected void process(Entity e) {
				Pos c1 = mPos.create(e);
				Pos c2 = mPos.create(e);
				Assert.assertEquals(c1, c2);
			}
		}
		createAndProcessWorld(new TestSystem());
	}

	@Test
	public void set_if_exists_should_recycle_existing_component() {

		@Wire(injectInherited = true)
		class TestSystem extends BasicSystem {
			@Override
			protected void process(Entity e) {
				Pos c1 = mPos.create(e);
				Pos c2 = mPos.set(e, true);
				Assert.assertEquals(c1, c2);
			}
		}
		createAndProcessWorld(new TestSystem());
	}

	@Test
	public void set_if_exists_should_remove_component() {

		@Wire(injectInherited = true)
		class TestSystem extends BasicSystem {
			@Override
			protected void process(Entity e) {
				mPos.create(e);
				mPos.set(e,false);
				Assert.assertFalse(mPos.has(e));
			}
		}
		createAndProcessWorld(new TestSystem());
	}

	@Test
	public void remove_if_exists_should_remove_component() {

		@Wire(injectInherited = true)
		class TestSystem extends BasicSystem {
			@Override
			protected void process(Entity e) {
				mPos.create(e);
				mPos.remove(e);
				Assert.assertFalse(mPos.has(e));
			}
		}
		createAndProcessWorld(new TestSystem());
	}

	@Test
	public void remove_if_missing_should_not_explode() {

		@Wire(injectInherited = true)
		class TestSystem extends BasicSystem {
			@Override
			protected void process(Entity e) {
				mPos.remove(e);
				Assert.assertFalse(mPos.has(e));
			}
		}
		createAndProcessWorld(new TestSystem());
	}

	@Test
	public void set_if_missing_should_not_explode() {

		@Wire(injectInherited = true)
		class TestSystem extends BasicSystem {
			@Override
			protected void process(Entity e) {
				mPos.set(e, false);
				Assert.assertFalse(mPos.has(e));
			}
		}
		createAndProcessWorld(new TestSystem());
	}

	@Test
	public void getsafe_with_fallback_should_return_fallback_when_component_missing() {

		@Wire(injectInherited = true)
		class TestSystem extends BasicSystem {

			private Pos fallbackPos = new Pos(10,10);

			@Override
			protected void process(Entity e) {
				Assert.assertEquals("expected to return fallback.", fallbackPos, mPos.getSafe(e, fallbackPos));
			}
		}
		createAndProcessWorld(new TestSystem());
	}

	@Test
	public void getsafe_with_fallback_should_return_component_when_available() {

		@Wire(injectInherited = true)
		class TestSystem extends BasicSystem {

			private Pos fallbackPos = new Pos(10,10);

			@Override
			protected void process(Entity e) {
				final Pos pos = mPos.create(e);
				Assert.assertEquals("expected to return component.", pos, mPos.getSafe(e, fallbackPos));
			}
		}
		createAndProcessWorld(new TestSystem());
	}

	@Test
	public void getsafe_with_null_fallback_should_return_null() {

		@Wire(injectInherited = true)
		class TestSystem extends BasicSystem {

			private Pos fallbackPos = new Pos(10,10);

			@Override
			protected void process(Entity e) {
				Assert.assertNull(mPos.getSafe(e, null));
			}
		}
		createAndProcessWorld(new TestSystem());
	}

	protected void createAndProcessWorld(BaseSystem system) {
		final World world = new World(new WorldConfigurationBuilder()
				.with(system)
				.with(new ExtendedComponentMapperPlugin())
				.build());
		world.createEntity().edit().create(TestMarker.class);
		world.process();
	}

	@Test
	public void create_right_after_entity_creation_should_not_throw_exception() {
		@Wire(injectInherited = true)
		class TestSystem extends BasicSystem {
			@Override
			protected void process(Entity e) {
				final Entity t1 = world.createEntity();
				Pos c1 = mPos.create(t1);
				Assert.assertNotNull(c1);
			}
		}
		createAndProcessWorld(new TestSystem());
	}


	@Test
	public void remove_right_after_entity_creation_should_not_throw_exception() {
		@Wire(injectInherited = true)
		class TestSystem extends BasicSystem {
			@Override
			protected void process(Entity e) {
				final Entity t1 = world.createEntity();
				mPos.remove(t1);
			}
		}
		createAndProcessWorld(new TestSystem());
	}

	@Test
	public void create_by_id_right_after_entity_creation_should_not_throw_exception() {
		@Wire(injectInherited = true)
		class TestSystem extends BasicSystem {
			@Override
			protected void process(Entity e) {
				final Entity t1 = world.createEntity();
				Pos c1 = mPos.create(t1.id);
				Assert.assertNotNull(c1);
			}
		}
		createAndProcessWorld(new TestSystem());
	}


	@Test
	public void remove_by_id_right_after_entity_creation_should_not_throw_exception() {
		@Wire(injectInherited = true)
		class TestSystem extends BasicSystem {
			@Override
			protected void process(Entity e) {
				final Entity t1 = world.createEntity();
				mPos.remove(t1.id);
			}
		}
		createAndProcessWorld(new TestSystem());
	}


	@Test
	public void mirror_existing_to_missing_should_create_and_copy_properties() {

		@Wire(injectInherited = true)
		class TestSystem extends BasicSystem {
			@Override
			protected void process(Entity e) {
				mPos.create(e).x = 99;
				final Entity e2 = world.createEntity();

				mPos.mirror(e2, e);
				Assert.assertEquals(99, mPos.get(e2).x);
			}
		}
		createAndProcessWorld(new TestSystem());
	}

	@Test
	public void mirror_existing_to_existing_should_override_properties() {

		@Wire(injectInherited = true)
		class TestSystem extends BasicSystem {
			@Override
			protected void process(Entity e) {
				mPos.create(e).x = 99;
				final Entity e2 = world.createEntity();
				mPos.create(e2).x = 80;
				mPos.mirror(e2, e);
				Assert.assertEquals(99,mPos.get(e2).x);
			}
		}
		createAndProcessWorld(new TestSystem());
	}

	@Test
	public void mirror_null_to_existing_should_remove_existing() {

		@Wire(injectInherited = true)
		class TestSystem extends BasicSystem {
			@Override
			protected void process(Entity e) {
				final Entity e2 = world.createEntity();
				mPos.create(e2).x = 80;
				mPos.mirror(e2, e);
				Assert.assertFalse(mPos.has(e2));
			}
		}
		createAndProcessWorld(new TestSystem());
	}

}
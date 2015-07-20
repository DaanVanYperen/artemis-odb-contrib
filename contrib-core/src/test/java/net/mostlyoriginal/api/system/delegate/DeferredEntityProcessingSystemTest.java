package net.mostlyoriginal.api.system.delegate;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.utils.EntityBuilder;
import net.mostlyoriginal.api.test.EmptyComponent;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Integration testing for deferred systems
 */
public class DeferredEntityProcessingSystemTest {

	static class TestDeferredSystem extends DeferredEntityProcessingSystem {
		public TestDeferredSystem(Aspect.Builder aspect, EntityProcessPrincipal principal) {
			super(aspect, principal);
		}

		@Override
		protected void process(Entity e) {
		}
	}

	@Test
	public void DeferredSystem_EntityAdded_RegisteredWithPrincipal() {

		EntityProcessPrincipal principal = mock(EntityProcessPrincipal.class);

		final WorldConfiguration config = new WorldConfiguration();
		config.setSystem(new TestDeferredSystem(Aspect.all(EmptyComponent.class), principal), true);
		// setup world and single entity.
		World w = new World(config);

		Entity myEntity = new EntityBuilder(w).with(EmptyComponent.class).build();
		w.process();

		// ensure it gets registered with the principal
		verify(principal, times(1)).registerAgent(eq(myEntity), any(EntityProcessAgent.class));
	}

	@Test
	public void DeferredSystem_EntityRemoved_UnregisteredFromPrincipal() {

		EntityProcessPrincipal principal = mock(EntityProcessPrincipal.class);

		// setup world and single entity.
		WorldConfiguration config = new WorldConfiguration();
		config.setSystem(new TestDeferredSystem(Aspect.all(EmptyComponent.class), principal), true);
		World w = new World(config);
		Entity myEntity = new EntityBuilder(w).with(EmptyComponent.class).build();
		w.process();
		myEntity.edit().deleteEntity();
		w.process();

		// ensure it gets registered with the principal
		verify(principal, times(1)).unregisterAgent(eq(myEntity), any(EntityProcessAgent.class));
	}
}
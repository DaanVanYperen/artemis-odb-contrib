package net.mostlyoriginal.api.system.core;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import net.mostlyoriginal.api.common.Penguin;
import net.mostlyoriginal.api.common.PenguinTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Daan van Yperen
 */
public class SpreadProcessingSystemTest extends PenguinTest {

	@Test
	public void Should_run_full_cycle_within_period() {

		final World world = bakeWorld(new MySpreadProcessingSystem(10, 1));
		world.delta=1;
		world.process();

		assertAllPenguinsPoked(1);
	}

	@Test
	public void Should_never_run_more_than_full_cycle_per_invocation() {

		final World world = bakeWorld(new MySpreadProcessingSystem(10, 1));
		world.delta=10;
		world.process();

		assertAllPenguinsPoked(1);
	}

	@Test
	public void Should_run_half_cycle_within_half_period() {
		final World world = bakeWorld(new MySpreadProcessingSystem(10, 1));
		world.delta=0.5f;
		world.process();

		for (int i=0;i<5;i++ ) {
			Assert.assertEquals("Penguin #" + i + " wrong pokes.", 1, penguins[i].pokes);
		}
		for (int i=5;i<10;i++ ) {
			Assert.assertEquals("Penguin #" + i + " wrong pokes.", 0, penguins[i].pokes);
		}
	}

	@Test
	public void Should_process_no_entities_When_delta_smaller_than_single_invocation() {

		final World world = bakeWorld(new MySpreadProcessingSystem(10, 1));
		world.delta=0.09f;
		world.process();

		assertAllPenguinsPoked(0);
	}

	@Test
	public void Should_process_entity_When_sufficient_small_deltas_combine() {
		final World world = bakeWorld(new MySpreadProcessingSystem(10, 1));
		world.delta=0.025f;
		world.process();
		world.process();
		world.process();
		world.process();

		Assert.assertEquals("Penguin #0 wrong pokes.", 1, penguins[0].pokes);
		for (int i=1;i<9;i++ ) {
			Assert.assertEquals("Penguin #" + i + " wrong pokes.", 0, penguins[i].pokes);
		}
	}

	@Test
	public void Should_not_step_over_entities_when_subscription_list_shrinks() {
		final World world = bakeWorld(new MySpreadProcessingSystem(10, 1));
		world.delta=0.5f;
		world.process();

		final int lastPokedIndex = getLastPokedIndex();

		// take out two entities that have been poked in this pass. This should shift the list.
		world.getEntity(lastPokedIndex-2).deleteFromWorld();
		world.getEntity(lastPokedIndex-3).deleteFromWorld();

		// since entity has been deleted the subscription list shrunk, moving the target index back one.
		// if the system does not compensate we could accidentally skip one or more pokes.

		// set all poked entries to an easily observable Value.
		for (int i = 0; i <= lastPokedIndex; i++) {
			penguins[i].pokes = INVALID_POKE_COUNT;
		}

		// process a bit more! notice: we delete entities, so the interval changed!
		world.delta=1/8f;
		world.process();

		for (int i = 0; i <= lastPokedIndex; i++) {
			Assert.assertEquals("Should continue at the new index.", INVALID_POKE_COUNT, penguins[i].pokes);
		}
		Assert.assertEquals("Should continue at the new index.", 1, penguins[lastPokedIndex + 1].pokes);
	}

	@Test
	public void Should_be_unaffected_by_deletion_of_entities_not_processed_yet() {
		final World world = bakeWorld(new MySpreadProcessingSystem(10, 1));
		world.delta=0.5f;
		world.process();

		final int lastPokedIndex = getLastPokedIndex();

		// take out two entities that have been poked in this pass. This should shift the list.
		world.getEntity(lastPokedIndex+1).deleteFromWorld();
		world.getEntity(lastPokedIndex+2).deleteFromWorld();

		// since entity has been deleted the subscription list shrunk, moving the target index back one.
		// if the system does not compensate we could accidentally skip one or more pokes.

		// set all poked entries to an easily observable Value.
		for (int i = 0; i <= lastPokedIndex+2; i++) {
			penguins[i].pokes = INVALID_POKE_COUNT;
		}

		// process a bit more! notice: we delete entities, so the interval changed!
		world.delta=1/8f;
		world.process();

		for (int i = 0; i <= lastPokedIndex+2; i++) {
			Assert.assertEquals("Should continue at the new index.", INVALID_POKE_COUNT, penguins[i].pokes);
		}
		Assert.assertEquals("Should continue at the new index.", 1, penguins[lastPokedIndex + 3].pokes);
	}

	private World bakeWorld(MySpreadProcessingSystem system) {
		return new World(new WorldConfiguration().setSystem(
				system));
	}


	private class MySpreadProcessingSystem extends SpreadProcessingSystem {

		private int penguins;

		public MySpreadProcessingSystem(int penguins, float roundTripTime) {
			super(Aspect.all(Penguin.class), roundTripTime);

			this.penguins = penguins;
		}

		@Override
		protected void initialize() {
			addPenguins(penguins, world);
		}

		@Override
		protected void process(Entity e) {
			e.getComponent(Penguin.class).pokes++;
		}

		private void addPenguins(int count, World world) {
			SpreadProcessingSystemTest.this.penguins = new Penguin[count];
			for (int i = 0; i < count; i++) {
				SpreadProcessingSystemTest.this.penguins[i] = new Penguin(i);
				world.createEntity().edit().add(SpreadProcessingSystemTest.this.penguins[i]);
			}
		}

	}

}
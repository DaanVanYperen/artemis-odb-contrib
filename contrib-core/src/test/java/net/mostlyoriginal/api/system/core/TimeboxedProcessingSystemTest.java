package net.mostlyoriginal.api.system.core;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import net.mostlyoriginal.api.common.Pokeable;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Daan van Yperen
 */
public class TimeboxedProcessingSystemTest {

	public static final int INVALID_POKE_COUNT = 999;
	public Pokeable[] pokables;

	@Test
	public void Should_stop_processing_after_allotted_time() {

		bakeWorld(new MyTimeboxedProcessingSystem(10, 0.01f, 0.05f,0)).process();

		Assert.assertEquals(1, pokables[0].pokes);
		Assert.assertEquals(1, pokables[3].pokes);
		Assert.assertEquals("Should've stopped poking.", 0, pokables[7].pokes);
	}

	@Test
	public void Should_start_where_left_off_last_round() {

		final World world = bakeWorld(new MyTimeboxedProcessingSystem(10, 0.01f, 0.02f,0));

		world.process();

		final int lastPokedIndex = getLastPokedIndex();

		// check preconditions.
		Assert.assertEquals("Expected last poked to have been poked!", 1, pokables[lastPokedIndex].pokes);
		Assert.assertEquals("Expected next penguin to poke to not be poked.", 0, pokables[lastPokedIndex + 1].pokes);

		world.process();

		Assert.assertEquals("Expected not to reach previously poked penguin.", 1, pokables[lastPokedIndex].pokes);
		Assert.assertEquals("Expected to continue next penguin in line.", 1, pokables[lastPokedIndex + 1].pokes);
	}

	private int getLastPokedIndex() {
		int highestPokes = 0;
		for (int i = 0; i < pokables.length; i++) {
			if (pokables[i].pokes < highestPokes) {
				return i - 1;
			}
			highestPokes = pokables[i].pokes;
		}
		return -1;
	}

	@Test
	public void Should_wrap_around_when_subscription_list_end_reached() {
		bakeWorld(new MyTimeboxedProcessingSystem(10, 0.01f, 0.02f,9)).process();
		Assert.assertEquals("Expected to wrap around to first penguin.", 1, pokables[0].pokes);
	}

	@Test
	public void Should_stop_after_one_full_cycle_even_if_time_remaining() {
		bakeWorld(new MyTimeboxedProcessingSystem(10, 0.01f, 9f,5)).process();
		Assert.assertEquals("Should run no more than 1 cycle.", 1, pokables[4].pokes);
		Assert.assertEquals("Should run no more than 1 cycle.", 1, pokables[5].pokes);
	}

	@Test
	public void Should_not_step_over_entities_when_subscription_list_shrinks() {
		final World world = bakeWorld(new MyTimeboxedProcessingSystem(20, 0.01f, 0.05f, 0));

		world.process();

		final int lastPokedIndex = getLastPokedIndex();

		// take out two entities that have been poked in this pass. This should shift the list.
		world.getEntity(lastPokedIndex-2).deleteFromWorld();
		world.getEntity(lastPokedIndex-3).deleteFromWorld();

		// since entity has been deleted the subscription list shrunk, moving the target index back one.
		// if the system does not compensate we could accidentally skip one or more pokes.

		// set all poked entries to an easily observable Value.
		for (int i = 0; i <= lastPokedIndex; i++) {
			pokables[i].pokes = INVALID_POKE_COUNT;
		}

		world.process();

		for (int i = 0; i <= lastPokedIndex; i++) {
			Assert.assertEquals("Should continue at the new index.", INVALID_POKE_COUNT, pokables[i].pokes);
		}
		Assert.assertEquals("Should continue at the new index.", 1, pokables[lastPokedIndex + 1].pokes);
	}


	@Test
	public void Should_be_unaffected_by_deletion_of_entities_not_processed_yet() {
		final World world = bakeWorld(new MyTimeboxedProcessingSystem(20, 0.01f, 0.05f, 0));

		world.process();

		final int lastPokedIndex = getLastPokedIndex();

		// take out two entities that have been poked in this pass. This should shift the list.
		world.getEntity(lastPokedIndex+2).deleteFromWorld();
		world.getEntity(lastPokedIndex+3).deleteFromWorld();

		// since entity has been deleted the subscription list shrunk, moving the target index back one.
		// if the system does not compensate we could accidentally skip one or more pokes.

		// set all poked entries to an easily observable Value.
		for (int i = 0; i <= lastPokedIndex; i++) {
			pokables[i].pokes = INVALID_POKE_COUNT;
		}

		world.process();

		for (int i = 0; i <= lastPokedIndex; i++) {
			Assert.assertEquals("Should continue at the new index.", INVALID_POKE_COUNT, pokables[i].pokes);
		}
		Assert.assertEquals("Should continue at the new index.", 1, pokables[lastPokedIndex + 1].pokes);
	}

	@Test
	public void Should_not_get_stuck_in_loop_when_empty() {
		bakeWorld(new MyTimeboxedProcessingSystem(0, 0f,1f,0)).process();
	}

	@Test
	public void Should_not_crash_when_invalid_index() throws Exception {
		final World world = bakeWorld(new MyTimeboxedProcessingSystem(10, 0.01f, 0.08f, 0));
		world.process();
		// purge entities. index is now invalid.
		for (int i = 0; i < 10; i++) {
			world.getEntity(i).deleteFromWorld();
		}
		// explode?
		world.process();
	}

	private World bakeWorld(MyTimeboxedProcessingSystem system) {
		return new World(new WorldConfiguration().setSystem(
				system));
	}

	private class MyTimeboxedProcessingSystem extends TimeboxedProcessingSystem {

		private int penguins;
		private float allottedTime;
		private float pokeSpeed;

		// we don't want to bind test to actual timers.
		private long fakeTime;

		public MyTimeboxedProcessingSystem(int penguins, float pokeSpeed, float allottedTime, int startIndex) {
			super(Aspect.all(Pokeable.class));
			this.penguins = penguins;
			this.pokeSpeed = pokeSpeed;
			this.allottedTime = allottedTime;
			this.index = startIndex;
		}

		@Override
		protected void initialize() {
			addPenguins(penguins, world);
		}

		@Override
		protected void process(Entity e) {
			e.getComponent(Pokeable.class).pokes++;
			fakeTime += pokeSpeed * NANOSECONDS_PER_SECOND;
		}

		@Override
		protected float getAllottedTime() {
			return allottedTime;
		}

		@Override
		protected long getTime() {
			return fakeTime;
		}

		private void addPenguins(int count, World world) {
			pokables = new Pokeable[count];
			for (int i = 0; i < count; i++) {
				pokables[i] = new Pokeable(i);
				world.createEntity().edit().add(pokables[i]);
			}
		}

	}
}
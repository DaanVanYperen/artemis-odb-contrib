package net.mostlyoriginal.api.system.core;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import com.artemis.World;
import com.artemis.WorldConfiguration;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class BaseIntervalSystemTest {

	private World world;
	private TestIntervalSystem sys;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	public void createWorld() {
		world = new World(new WorldConfiguration().setSystem(sys));
	}

	@Test
	public void test_system_processed_on_exact_time_interval() {
		sys = Mockito.spy(new TestIntervalSystem(1f));
		createWorld();
		world.delta = 1f;
		world.process();
		Mockito.verify(sys, times(1)).processSystem();
	}

	@Test
	public void test_system_not_processed_on_smaller_time_interval() {
		sys = Mockito.spy(new TestIntervalSystem(1f));
		createWorld();
		world.delta = 0.5f;
		world.process();
		Mockito.verify(sys, never()).processSystem();
	}

	@Test
	public void test_system_processed_twice_on_large_time_interval() {
		sys = Mockito.spy(new TestIntervalSystem(1f));
		createWorld();
		world.delta = 2.1f;
		world.process();
		world.process();
		Mockito.verify(sys, times(2)).processSystem();
	}

	@Test
	public void test_interval_delta_() {
		sys = new TestIntervalSystem(1f);
		createWorld();
		world.delta = 1.1f;
		world.process();
		assertEquals(1.1f, sys.getIntervalDelta(), 0.0001f);

		world.delta = 0.95f;
		world.process();
		assertEquals(0.95f, sys.getIntervalDelta(), 0.0001f);
	}

	// Simple implementation since BaseIntervalSystem is abstract class
	private static class TestIntervalSystem extends BaseIntervalSystem {

		public TestIntervalSystem(float interval) {
			super(interval);
		}

		@Override
		public void processSystem() {
			// do nothing
		}
	}
}

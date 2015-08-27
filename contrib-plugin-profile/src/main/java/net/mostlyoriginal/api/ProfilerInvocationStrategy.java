package net.mostlyoriginal.api;

import com.artemis.BaseSystem;
import com.artemis.SystemInvocationStrategy;
import com.artemis.World;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;

/**
 * {@link SystemInvocationStrategy} that will create a profiler for all systems that don't already have one
 * Can be used in addition to or instead of {@link com.artemis.annotations.Profile} annotation
 *
 * In addition creates {@link SystemProfiler} with name "Frame" for total frame time
 * It can be accessed with {@link SystemProfiler#get(String)}
 *
 * @author piotr-j
 */
public class ProfilerInvocationStrategy extends SystemInvocationStrategy {
	SystemProfiler total;
	SystemProfiler[] profilers;

	public ProfilerInvocationStrategy(World world) {
		total = SystemProfiler.create("Frame");
		total.setColor(1, 1, 1, 1);

		ImmutableBag<BaseSystem> systems = world.getSystems();
		profilers = new SystemProfiler[systems.size()];
		for (int i = 0; i < systems.size(); i++) {
			BaseSystem system = systems.get(i);
			SystemProfiler old = SystemProfiler.getFor(system);
			if (old == null) {
				profilers[i] = SystemProfiler.createFor(system, world);
			}
		}
	}

	@Override protected void process (Bag<BaseSystem> systems) {
		total.start();
		Object[] systemsData = systems.getData();
		for (int i = 0; i < systems.size(); i++) {
			updateEntityStates();

			BaseSystem system = (BaseSystem)systemsData[i];
			if (!system.isPassive()) {
				SystemProfiler profiler = profilers[i];
				if (profiler != null) profiler.start();
				system.process();
				if (profiler != null) profiler.stop();
			}
		}
		total.stop();
	}
}

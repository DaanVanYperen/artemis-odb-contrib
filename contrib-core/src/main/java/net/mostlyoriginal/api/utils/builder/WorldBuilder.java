package net.mostlyoriginal.api.utils.builder;

import com.artemis.BaseSystem;
import com.artemis.Manager;
import com.artemis.World;
import com.artemis.utils.Bag;

/**
 * World builder.
 *
 * @author Daan van Yperen
 */
public class WorldBuilder {

	private Bag<Manager> managers;
	private Bag<SystemRegistration> systems;

	public WorldBuilder() {
		reset();
	}

	/** Assemble world with managers and systems. */
	public World build() {
		World world = new World();
		registerManagers(world);
		registerSystems(world);
		reset();
		return world;
	}

	private void registerManagers(World world) {
		for (Manager manager : managers) {
			world.setManager(manager);
		}
	}

	private void registerSystems(World world) {
		for (SystemRegistration system : systems) {
			world.setSystem(system.system, system.passive);
		}
	}

	/** Reset builder */
	private void reset() {
		managers = new Bag<>();
		systems = new Bag<>();
	}

	/** Return world, built and initialized */
	public World initialize()
	{
		World world = build();
		world.initialize();
		return world;
	}

	/**
	 * Add one or more managers to the world.
	 * Managers are always added before systems.
	 */
	public WorldBuilder with(Manager ... managers) {
		for (Manager manager : managers) {
			this.managers.add(manager);
		}
		return this;
	}

	/**
	 * Register active system(s).
	 * Order is preserved.
	 */
	public WorldBuilder with(BaseSystem... systems) {
		addSystems(systems, false);
		return this;
	}

	/**
	 * Register passive systems.
	 * Order is preserved.
	 */
	public WorldBuilder withPassive(BaseSystem ... systems) {
		addSystems(systems, true);
		return this;
	}

	private void addSystems(BaseSystem[] systems, boolean passive) {
		for (BaseSystem system : systems) {
			this.systems.add(new SystemRegistration(system, passive));
		}
	}

	public static class SystemRegistration {
		public final BaseSystem system;
		public final boolean passive;

		public SystemRegistration(BaseSystem system, boolean passive) {
			this.system = system;
			this.passive = passive;
		}
	}
}

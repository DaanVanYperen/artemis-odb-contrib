package net.mostlyoriginal.api.utils.builder;

import com.artemis.BaseSystem;
import com.artemis.Manager;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.utils.Bag;

/**
 * World builder.
 *
 * @author Daan van Yperen
 */
public class WorldConfigurationBuilder {

	private Bag<Manager> managers;
	private Bag<SystemRegistration> systems;

	public WorldConfigurationBuilder() {
		reset();
	}

	/** Assemble world with managers and systems.
	 *
	 * Deprecated: World Configuration
	 */
	public WorldConfiguration build() {
		final WorldConfiguration config = new WorldConfiguration();
		registerManagers(config);
		registerSystems(config);
		reset();
		return config;
	}

	private void registerManagers(WorldConfiguration config) {
		for (Manager manager : managers) {
			config.setManager(manager);
		}
	}

	private void registerSystems(WorldConfiguration config) {
		for (SystemRegistration system : systems) {
			config.setSystem(system.system, system.passive);
		}
	}

	/** Reset builder */
	private void reset() {
		managers = new Bag<>();
		systems = new Bag<>();
	}

	/**
	 * Add one or more managers to the world.
	 * Managers are always added before systems.
	 */
	public WorldConfigurationBuilder with(Manager ... managers) {
		for (Manager manager : managers) {
			this.managers.add(manager);
		}
		return this;
	}

	/**
	 * Register active system(s).
	 * Order is preserved.
	 */
	public WorldConfigurationBuilder with(BaseSystem... systems) {
		addSystems(systems, false);
		return this;
	}

	/**
	 * Register passive systems.
	 * Order is preserved.
	 */
	public WorldConfigurationBuilder withPassive(BaseSystem ... systems) {
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

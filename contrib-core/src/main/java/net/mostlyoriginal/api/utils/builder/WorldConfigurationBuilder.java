package net.mostlyoriginal.api.utils.builder;

import com.artemis.BaseSystem;
import com.artemis.Manager;
import com.artemis.WorldConfiguration;
import com.artemis.injection.CachedInjector;
import com.artemis.injection.FieldHandler;
import com.artemis.injection.FieldResolver;
import com.artemis.injection.InjectionCache;
import com.artemis.utils.Bag;
import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.ReflectionException;
import net.mostlyoriginal.api.plugin.common.ArtemisPlugin;

/**
 * World builder.
 *
 * @author Daan van Yperen
 */
public class WorldConfigurationBuilder {

	private Bag<Manager> managers;
	private Bag<SystemRegistration> systems;
	private Bag<ArtemisPlugin> plugins;
	private Bag<FieldResolver> fieldResolvers;
	private ArtemisPlugin activePlugin;
	private final InjectionCache cache;

	public WorldConfigurationBuilder() {
		reset();
		cache = new InjectionCache();
	}

	/**
	 * Assemble world with managers and systems.
	 * <p/>
	 * Deprecated: World Configuration
	 */
	public WorldConfiguration build() {
		appendPlugins();
		final WorldConfiguration config = new WorldConfiguration();
		registerManagers(config);
		registerSystems(config);
		registerFieldResolvers(config);
		reset();
		return config;
	}

	/**
	 * Append plugin configurations.
	 * Supports plugins registering plugins.
	 */
	private void appendPlugins() {
		int i = 0;
		while (i < plugins.size()) {
			activePlugin = plugins.get(i);
			activePlugin.setup(this);
			i++;
		}
		activePlugin = null;
	}

	protected void registerFieldResolvers(WorldConfiguration config) {

		if (fieldResolvers.size() > 0) {
			// instance default field handelr
			final FieldHandler fieldHandler = new FieldHandler(new InjectionCache());

			for (FieldResolver fieldResolver : fieldResolvers) {
				fieldHandler.addFieldResolver(fieldResolver);
			}

			config.setInjector(new CachedInjector().setFieldHandler(fieldHandler));
		}
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

	/**
	 * Reset builder
	 */
	private void reset() {
		managers = new Bag<>();
		systems = new Bag<>();
		fieldResolvers = new Bag<>();
		plugins = new Bag<>();
	}

	public WorldConfigurationBuilder register(FieldResolver... fieldResolvers) {
		for (FieldResolver fieldResolver : fieldResolvers) {
			this.fieldResolvers.add(fieldResolver);
		}
		return this;
	}

	/**
	 * Add one or more managers to the world.
	 * Managers are always added before systems.
	 */
	public WorldConfigurationBuilder with(Manager... managers) {
		for (Manager manager : managers) {

			if (containsManagersOfType(manager.getClass())) {
				throw new WorldConfigurationException("Manager of type " + manager.getClass() + " registered twice. Only once allowed.");
			}

			this.managers.add(manager);
		}
		return this;
	}

	/**
	 * Ensure managers/systems are added to the world.
	 *
	 * @param types required managers.
	 */
	@SuppressWarnings("unchecked")
	public final void dependsOn(Class ... types) {
		try {
			for (Class type : types) {
				switch ( cache.getFieldClassType(type) )
				{
					case SYSTEM:
						dependsOnSystem(type);
						break;
					case MANAGER:
						dependsOnManager(type);
						break;
					default:
						throw new WorldConfigurationException("Unsupported type. Only supports managers and systems.");
				}
			}
		} catch (ReflectionException e) {
			throw new WorldConfigurationException("Unable to instance manager via reflection.", e);
		}
	}

	protected void dependsOnManager(Class<? extends Manager> type) throws ReflectionException {
		if (!containsManagersOfType(type)) {
			this.managers.add(ClassReflection.newInstance(type));
		}
	}

	protected void dependsOnSystem(Class<? extends BaseSystem> type) throws ReflectionException {
		if (!containsSystemOfType(type)) {
			this.systems.add(new SystemRegistration(ClassReflection.newInstance(type), false));
		}
	}

	/**
	 * Register active system(s).
	 * Order is preserved.
	 */
	public WorldConfigurationBuilder with(BaseSystem... systems) {
		addSystems(systems, false);
		return this;
	}


	public WorldConfigurationBuilder with(ArtemisPlugin... plugins) {
		addPlugins(plugins);
		return this;
	}

	/**
	 * Register passive systems.
	 * Order is preserved.
	 */
	public WorldConfigurationBuilder withPassive(BaseSystem... systems) {
		addSystems(systems, true);
		return this;
	}

	private void addSystems(BaseSystem[] systems, boolean passive) {
		for (BaseSystem system : systems) {

			if (containsSystemOfType(system.getClass())) {
				throw new WorldConfigurationException("System of type " + system.getClass() + " registered twice. Only once allowed.");
			}

			this.systems.add(new SystemRegistration(system, passive));
		}
	}

	private boolean containsSystemOfType(Class<? extends BaseSystem> type) {
		for (SystemRegistration systemRegistration : systems) {
			if (systemRegistration.system.getClass() == type) {
				return true;
			}
		}
		return false;
	}

	private boolean containsManagersOfType(Class<? extends Manager> type) {
		for (Manager manager : managers) {
			if (manager.getClass() == type) {
				return true;
			}
		}
		return false;
	}


	private void addPlugins(ArtemisPlugin[] plugins) {
		for (ArtemisPlugin plugin : plugins) {
			if (!this.plugins.contains(plugin)) {
				this.plugins.add(plugin);
			}
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

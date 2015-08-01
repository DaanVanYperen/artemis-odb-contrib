package net.mostlyoriginal.api.utils.builder;

import com.artemis.BaseSystem;
import com.artemis.Manager;
import com.artemis.WorldConfiguration;
import com.artemis.injection.CachedInjector;
import com.artemis.injection.FieldHandler;
import com.artemis.injection.FieldResolver;
import com.artemis.injection.InjectionCache;
import com.artemis.utils.Bag;
import com.artemis.utils.Sort;
import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.ReflectionException;
import net.mostlyoriginal.api.plugin.common.ArtemisPlugin;

/**
 * World builder.
 *
 * @author Daan van Yperen
 */
public class WorldConfigurationBuilder {

	private Bag<Registerable<Manager>> managers;
	private Bag<Registerable<BaseSystem>> systems;
	private Bag<Registerable<FieldResolver>> fieldResolvers;

	private Bag<ArtemisPlugin> plugins;
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

	/** add custom field handler with resolvers. */
	protected void registerFieldResolvers(WorldConfiguration config) {

		if (fieldResolvers.size() > 0) {
			Sort.instance().sort(fieldResolvers);
			// instance default field handler
			final FieldHandler fieldHandler = new FieldHandler(new InjectionCache());

			for (Registerable<FieldResolver> registerable : fieldResolvers) {
				fieldHandler.addFieldResolver(registerable.item);
			}

			config.setInjector(new CachedInjector().setFieldHandler(fieldHandler));
		}
	}

	/** add managers to config. */
	private void registerManagers(WorldConfiguration config) {
		Sort.instance().sort(managers);
		for (Registerable<Manager> registerable : managers) {
			config.setManager(registerable.item);
		}
	}

	/** add systems to config. */
	private void registerSystems(WorldConfiguration config) {
		Sort.instance().sort(systems);
		for (Registerable<BaseSystem> registerable : systems) {
			config.setSystem(registerable.item, registerable.passive);
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

	/**
	 * Add field resolver.
	 *
	 * @param fieldResolvers
	 * @return this
	 */
	public WorldConfigurationBuilder register(FieldResolver... fieldResolvers) {
		for (FieldResolver fieldResolver : fieldResolvers) {
			this.fieldResolvers.add(Registerable.of(fieldResolver));
		}
		return this;
	}

	/**
	 * Add one or more managers to the world with default priority.
	 *
	 * Managers track priority separate from system priority, and are always added before systems.
	 *
	 * @param managers
	 *              Managers to add. Will be added in passed order.
	 * @return this
	 */
	public WorldConfigurationBuilder with(Manager... managers) {
		return with(Priority.NORMAL, managers);
	}

	/**
	 * Add one or more managers to the world.
	 *
	 * Managers track priority separate from system priority, and are always added before systems.
	 *
	 * @param priority
	 *              Priority of managers. Higher priority managers are registered before lower priority managers.
	 * @param managers
	 *              Managers to add. Will be added in passed order.
	 * @throws WorldConfigurationException if registering the same class twice.
	 * @return this
	 */
	public WorldConfigurationBuilder with(int priority, Manager... managers) {
		for (Manager manager : managers) {

			if (containsType(this.managers, manager.getClass())) {
				throw new WorldConfigurationException("Manager of type " + manager.getClass() + " registered twice. Only once allowed.");
			}

			this.managers.add(Registerable.of(manager, priority));
		}
		return this;
	}

	/**
	 * Ensure managers/systems are added to the world at normal priority.
	 *
	 * Managers track priority separate from system priority, and are always added before systems.
	 *
	 * @param types
	 *              required managers and/or systems.
	 * @return this
	 */
	public final WorldConfigurationBuilder dependsOn(Class... types) {
		return dependsOn(Priority.NORMAL, types);
	}

	/**
	 * Ensure managers/systems are added to the world.
	 *
	 * Managers track priority separate from system priority, and are always added before systems.
	 *
	 * @param types
	 *              required managers and/or systems.
	 * @param priority
	 *              Higher priority are registered first.
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public final WorldConfigurationBuilder dependsOn(int priority, Class... types) {
		try {
			for (Class type : types) {
				switch (cache.getFieldClassType(type)) {
					case SYSTEM:
						dependsOnSystem(priority, type);
						break;
					case MANAGER:
						dependsOnManager(priority, type);
						break;
					default:
						throw new WorldConfigurationException("Unsupported type. Only supports managers and systems.");
				}
			}
		} catch (ReflectionException e) {
			throw new WorldConfigurationException("Unable to instance manager via reflection.", e);
		}
		return this;
	}

	protected void dependsOnManager(int priority, Class<? extends Manager> type) throws ReflectionException {
		if (!containsType(managers, type)) {
			this.managers.add(Registerable.of(ClassReflection.newInstance(type), priority));
		}
	}

	protected void dependsOnSystem(int priority, Class<? extends BaseSystem> type) throws ReflectionException {
		if (!containsType(systems,type)) {
			this.systems.add(Registerable.of(ClassReflection.newInstance(type), priority));
		}
	}

	/**
	 * Register active system(s).
	 *
	 * Systems track priority separate from manager priority, and are always added after managers.
	 *
	 * @param systems systems to add, order is preserved.
	 * @param priority priority of added systems, higher priority are added before lower priority.
	 * @return this
	 */
	public WorldConfigurationBuilder with(int priority, BaseSystem... systems) {
		addSystems(priority, systems, false);
		return this;
	}

	/**
	 * Register active system(s).
	 *
	 * Systems track priority separate from manager priority, and are always added after managers.
	 *
	 * @param systems systems to add, order is preserved.
	 * @return this
	 */
	public WorldConfigurationBuilder with(BaseSystem... systems) {
		addSystems(Priority.NORMAL, systems, false);
		return this;
	}


	/**
	 * Add plugins to world.
	 *
	 * Upon build plugins will be called to register dependencies.
	 *
	 * @param plugins Plugins to add.
	 * @return this
	 */
	public WorldConfigurationBuilder with(ArtemisPlugin... plugins) {
		addPlugins(plugins);
		return this;
	}

	/**
	 * Register passive systems.
	 *
	 * Systems track priority separate from manager priority, and are always added after managers.
	 *
	 * @param systems systems to add, order is preserved.
	 * @param priority priority of added systems, higher priority are added before lower priority.
	 * @return this
	 */
	public WorldConfigurationBuilder withPassive(int priority, BaseSystem... systems) {
		addSystems(priority, systems, true);
		return this;
	}

	/**
	 * Register passive systems with normal priority.
	 *
	 * Systems track priority separate from manager priority, and are always added after managers.
	 *
	 * @param systems systems to add, order is preserved.
	 * @return this
	 */
	public WorldConfigurationBuilder withPassive(BaseSystem... systems) {
		addSystems(Priority.NORMAL, systems, true);
		return this;
	}

	/** helper to queue systems for registration. */
	private void addSystems(int priority, BaseSystem[] systems, boolean passive) {
		for (BaseSystem system : systems) {

			if (containsType(this.systems, system.getClass())) {
				throw new WorldConfigurationException("System of type " + system.getClass() + " registered twice. Only once allowed.");
			}

			this.systems.add(new Registerable<>(system, priority, passive));
		}
	}

	/**
	 * Check if bag of registerables contains any of passed type.
	 *
	 * @param items bag of registerables.
	 * @param type type to check for.
	 * @return {@code true} if found {@code false} if none.
	 */
	@SuppressWarnings("unchecked")
	private boolean containsType(Bag items, Class type) {
		for (Registerable<?> registration : (Bag<Registerable<?>>)items) {
			if (registration.itemType == type) {
				return true;
			}
		}
		return false;
	}

	/** Add new plugins. */
	private void addPlugins(ArtemisPlugin[] plugins) {
		for (ArtemisPlugin plugin : plugins) {
			if (!this.plugins.contains(plugin)) {
				this.plugins.add(plugin);
			}
		}
	}

	public static abstract class Priority {
		public static final int LOWEST  = Integer.MIN_VALUE;
		public static final int LOW = -10000;
		public static final int NORMAL  = 0;
		public static final int HIGH = 10000;
		public static final int HIGHEST = Integer.MAX_VALUE;
	}
}

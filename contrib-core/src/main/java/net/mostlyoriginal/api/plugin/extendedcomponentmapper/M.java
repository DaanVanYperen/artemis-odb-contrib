package net.mostlyoriginal.api.plugin.extendedcomponentmapper;

import com.artemis.*;

/**
 * Extended Component Mapper.
 *
 * Wire support provided by {@see ExtendedComponentMapperFieldResolver}.
 *
 * @author Daan van Yperen
 */
public class M<A extends Component> {

	private final ComponentMapper<A> mapper;
	private final EntityTransmuter createTransmuter;
	private final EntityTransmuter removeTransmuter;
	private final Entity flyweight;

	@SuppressWarnings("unchecked")
	public M( Class<? extends Component> type, World world) {
		this.mapper = (ComponentMapper<A>) world.getMapper(type);
		flyweight = Entity.createFlyweight(world);
		createTransmuter = new EntityTransmuterFactory(world).add(type).build();
		removeTransmuter = new EntityTransmuterFactory(world).remove(type).build();
	}

	/**
	 * Fast and safe retrieval of a component for this entity.
	 * If the entity does not have this component then fallback is returned.
	 *
	 * @param entityId Entity that should possess the component
	 * @param fallback fallback component to return, or {@code null} to return null.
	 * @return the instance of the component
	 */
	public A getSafe(int entityId, A fallback) {
		final A c = getSafe(entityId);
		return (c != null) ? c : fallback;
	}

	/**
	 * Fast and safe retrieval of a component for this entity.
	 * If the entity does not have this component then fallback is returned.
	 *
	 * @param entity   Entity that should possess the component
	 * @param fallback fallback component to return, or {@code null} to return null.
	 * @return the instance of the component
	 */
	public A getSafe(Entity entity, A fallback) {
		return getSafe(entity.getId(), fallback);
	}

	/**
	 * Create component for this entity.
	 * Will avoid creation if component preexists.
	 *
	 * @param entityId the entity that should possess the component
	 * @return the instance of the component.
	 */
	public A create(int entityId) {
		A component = getSafe(entityId);
		if (component == null) {
			createTransmuter.transmute(asFlyweight(entityId));
			component = get(entityId);
		}
		return component;
	}

	/**
	 * Setup flyweight with ID and return.
	 * Cannot count on just created entities being resolvable
	 * in world, which can break transmuters.
	 */
	private Entity asFlyweight(int entityId) {
		flyweight.id = entityId;
		return flyweight;
	}

	/**
	 * Remove component from entity.
	 * Does nothing if already removed.
	 *
	 * @param entityId
	 */
	public void remove(int entityId) {
		if ( has(entityId) )
		{
			removeTransmuter.transmute(asFlyweight(entityId));
		}
	}

	/**
	 * Remove component from entity.
	 * Does nothing if already removed.
	 *
	 * @param entity entity to remove.
	 */
	public void remove(Entity entity) {
		remove(entity.getId());
	}

	/**
	 * Create component for this entity.
	 * Will avoid creation if component preexists.
	 *
	 * @param entity the entity that should possess the component
	 * @return the instance of the component.
	 */
	public A create(Entity entity) {
		return create(entity.getId());
	}

	public A get(Entity e) throws ArrayIndexOutOfBoundsException {
		return mapper.get(e);
	}

	public A getSafe(Entity e, boolean forceNewInstance) {
		return mapper.getSafe(e, forceNewInstance);
	}

	public A get(int entityId) throws ArrayIndexOutOfBoundsException {
		return mapper.get(entityId);
	}

	public A getSafe(int entityId) {
		return mapper.getSafe(entityId);
	}

	public boolean has(Entity e) throws ArrayIndexOutOfBoundsException {
		return mapper.has(e);
	}

	public A getSafe(Entity e) {
		return mapper.getSafe(e);
	}

	public boolean has(int entityId) {
		return mapper.has(entityId);
	}

	public static <T extends Component> ComponentMapper<T> getFor(Class<T> type, World world) {
		return ComponentMapper.getFor(type, world);
	}

	public A get(Entity e, boolean forceNewInstance) throws ArrayIndexOutOfBoundsException {
		return mapper.get(e, forceNewInstance);
	}

	public A get(int entityId, boolean forceNewInstance) throws ArrayIndexOutOfBoundsException {
		return mapper.get(entityId, forceNewInstance);
	}

	public A getSafe(int entityId, boolean forceNewInstance) {
		return mapper.getSafe(entityId, forceNewInstance);
	}
}

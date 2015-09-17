package net.mostlyoriginal.api.plugin.extendedcomponentmapper;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.reflect.ClassReflection;
import net.mostlyoriginal.api.component.common.Mirrorable;

/**
 * Extended Component Mapper.
 *
 * Wire support provided by {@see ExtendedComponentMapperFieldResolver}.
 *
 * @author Daan van Yperen
 */
public class M<A extends Component> {

	private final ComponentMapper<A> mapper;
	private final boolean isMirrorable;

	@SuppressWarnings("unchecked")
	public M( Class<? extends Component> type, World world) {
		this.mapper = (ComponentMapper<A>) world.getMapper(type);
		isMirrorable = ClassReflection.isAssignableFrom(net.mostlyoriginal.api.component.common.Mirrorable.class, type);
	}

	public boolean isMirrorable() {
		return isMirrorable;
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
		return mapper.getSafe(entity.getId(), fallback);
	}

	/**
	 * Create component for this entity.
	 * Will avoid creation if component preexists.
	 *
	 * @param entityId the entity that should possess the component
	 * @return the instance of the component.
	 */
	public A create(int entityId) {
		return mapper.create(entityId);
	}

	/**
	 * Create or remove a component from an entity.
	 *
	 * Does nothing if already removed or created respectively.
	 *
	 * @param entityId Entity id to change.
	 * @param value {@code true} to create component (if missing), {@code false} to remove (if exists).
	 * @return the instance of the component, or {@code null} if removed.
	 */
	public A set(int entityId, boolean value) {
		return mapper.set(entityId, value);
	}

	/**
	 * Mirror component between entities.
	 *
	 * 1. calls target#set(source) if source exists.
	 * 2. removes target if source is missing.
	 *
	 * Requires component to extend from {@code ExtendedComponent}.
	 *
	 * @param targetId target entity id
	 * @param sourceId source entity id
	 * @return the instance of the component, or {@code null} if removed.
	 */
	@SuppressWarnings("unchecked")
	public A mirror(int targetId, int sourceId) {
		if ( !isMirrorable) {
			throw new RuntimeException("Component does not extend ExtendedComponent<T> or just Mirrorable<T>, required for #set.");
		}

		final A source = getSafe(sourceId);
		if ( source != null ) {
			return (A) ((Mirrorable)create(targetId)).set(source);
		} else {
			remove(targetId);
			return null;
		}
	}

	/**
	 * Mirror component between entities.
	 *
	 * 1. calls target#set(source) if source exists.
	 * 2. removes target if source is missing.
	 *
	 * Requires component to extend from {@code ExtendedComponent}.
	 *
	 * @param target target entity
	 * @param source source entity
	 * @return the instance of the component, or {@code null} if removed.
	 */
	public A mirror(Entity target, Entity source) {
		return mirror(target.getId(), source.getId());
	}

	/**
	 * Create or remove a component from an entity.
	 *
	 * Does nothing if already removed or created respectively.
	 *
	 * @param entity Entity to change.
	 * @param value {@code true} to create component (if missing), {@code false} to remove (if exists).
	 * @return the instance of the component, or {@code null} if removed.
	 */
	public A set(Entity entity, boolean value) {
		return mapper.set(entity.getId(), value);
	}

	/**
	 * Remove component from entity.
	 * Does nothing if already removed.
	 *
	 * @param entityId
	 */
	public void remove(int entityId) {
		mapper.remove(entityId);
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
		return mapper.create(entity.getId());
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

	public static <T extends Component> M<T> getFor(Class<T> type, World world) {
		return world.getManager(ExtendedComponentMapperManager.class).getFor(type);
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

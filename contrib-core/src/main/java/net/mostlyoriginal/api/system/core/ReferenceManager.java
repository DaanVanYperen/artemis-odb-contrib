package net.mostlyoriginal.api.system.core;

import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.TagManager;
import com.artemis.managers.UuidEntityManager;
import net.mostlyoriginal.api.utils.reference.EntityReference;

import java.util.UUID;

/**
 * Fetch safe entity references.
 *
 * @author Daan van Yperen
 * @TODO make this pooled.
 */
@Wire
public class ReferenceManager extends PassiveSystem {

	private static final EntityReference NONE = new EntityReferenceImpl();

	// Should be part of world.
	private UuidEntityManager uem;
	private TagManager tagManager;

	/** Return entity reference of entity, which will expire when entity gets deleted from the world. */
	public EntityReference of( Entity entity )
	{
		return new EntityReferenceImpl(entity);
	}

	/** REturn entity reference that resolves by tag. */
	public EntityReference ofTag( String tag )
	{
		return new TagEntityReference(tagManager, tag);
	}

	/** Return empty reference. */
	public EntityReference none()
	{
		return NONE;
	}

	/**
	 * Invalidates volatile entity when UUID invalidates.
	 *
	 * @todo deserialize entity.
	 * @author Daan van Yperen
	 */
	private static class EntityReferenceImpl implements net.mostlyoriginal.api.utils.reference.EntityReference {

	    private UUID uuid;
	    private transient Entity entity;

		public EntityReferenceImpl() {
			this.uuid = null;
			this.entity = null;
		}

		public EntityReferenceImpl(Entity entity) {
	        this.entity = entity;
	        this.uuid = entity.getUuid();
	    }

		@Override
	    public Entity get() {
			return entity != null && entity.getUuid().equals(uuid) ? entity : null;
	    }
	}

	/** Tag based entity reference. */
	private static class TagEntityReference implements EntityReference {

	    private transient TagManager tagManager;
	    private String tag;

	    public TagEntityReference( TagManager tagManager, String tag ) {
	        this.tagManager = tagManager;
	        this.tag = tag;
	    }

	    @Override
	    public Entity get() {
	        return tagManager.getEntity(tag);
	    }
	}
}

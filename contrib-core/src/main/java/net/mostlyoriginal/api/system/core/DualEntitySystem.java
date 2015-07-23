package net.mostlyoriginal.api.system.core;

import com.artemis.*;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;

/**
 * Entity system for processing two sets of entities each matching their
 * own aspect.
 *
 * @author Daan van Yperen
 */
public abstract class DualEntitySystem extends BaseSystem implements EntitySubscription.SubscriptionListener {

	protected final Aspect.Builder matcherA;
	protected final Aspect.Builder matcherB;

	protected EntitySubscription subscriptionA;
	protected EntitySubscription subscriptionB;

	/**
	 * Creates an entity system that uses the specified aspects as a matcher
	 * against entities.
	 *
	 * @param aspectA to match set A
	 * @param aspectB to match set B
	 */
	public DualEntitySystem(Aspect.Builder aspectA, Aspect.Builder aspectB) {
		super();

		if (aspectA == null || aspectB == null) {
			throw new NullPointerException("Aspect.Builder was null; to use systems which " +
					"do not subscribe to entities, extend BaseSystem directly.");
		}

		this.matcherA = aspectA;
		this.matcherB = aspectB;
	}

	protected void setWorld(World world) {
		super.setWorld(world);

		subscriptionA = getSubscription(matcherA);
		subscriptionB = getSubscription(matcherB);

		subscriptionA.addSubscriptionListener(this);
		subscriptionB.addSubscriptionListener(this);
	}

	public EntitySubscription getSubscription(Aspect.Builder aspectA) {
		final AspectSubscriptionManager sm = world.getManager(AspectSubscriptionManager.class);
		return sm.get(aspectA);
	}

	@Override
	public void inserted(ImmutableBag<Entity> entities) {
		final Object[] data = ((Bag<Entity>) entities).getData();
		for (int i = 0, s = entities.size(); s > i; i++) {
			inserted((Entity) data[i]);
		}
	}

	@Override
	public void removed(ImmutableBag<Entity> entities) {
		final Object[] data = ((Bag<Entity>) entities).getData();
		for (int i = 0, s = entities.size(); s > i; i++) {
			removed((Entity) data[i]);
		}
	}

	/**
	 * Called if the system has received a entity it is interested in, e.g
	 * created or a component was added to it.
	 *
	 * @param e the entity that was added to this system
	 */
	protected void inserted(Entity e) {
	}

	/**
	 * Called if a entity was removed from this system, e.g deleted or had one
	 * of it's components removed.
	 *
	 * @param e the entity that was removed from this system
	 */
	protected void removed(Entity e) {
	}
}

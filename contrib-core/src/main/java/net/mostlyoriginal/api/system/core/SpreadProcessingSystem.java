package net.mostlyoriginal.api.system.core;


import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.World;
import com.artemis.utils.IntBag;


/**
 * Spread entity processing.
 * <p/>
 *
 * The system spreads out invocations while maintaining
 * a fixed processing interval per entity.
 *
 * Use to spread out work over multiple frames.
 *
 * The more entities are added, the smaller the interval
 * between {@see #process(Entity)} invocations.
 *
 * @author Adrian Papari
 * @author Daan van Yperen
 */
public abstract class SpreadProcessingSystem extends EntitySystem {


	protected int index;
	protected float roundTripTime;
	protected float entitiesToProcess=0;
	protected int lastProcessedEntityId =-1;
	protected Entity flyweight;

	/**
	 * Creates a new SpreadProcessingSystem.
	 *
	 * @param aspect        the aspect to match entites
	 * @param roundTripTime time required to cycle through all entities.
	 */
	public SpreadProcessingSystem(Aspect.Builder aspect, float roundTripTime) {
		super(aspect);
		setRoundTripTime(roundTripTime);
	}

	@Override
	protected void setWorld(World world) {
		super.setWorld(world);
		flyweight = createFlyweightEntity();
	}

	/**
	 * Process a entity this system is interested in.
	 *
	 * @param e the entity to process
	 */
	protected abstract void process(Entity e);

	@Override
	protected void processSystem() {

		final IntBag entities = subscription.getEntities();

		final int processedPerSecond = (int) (( entities.size() / getRoundTripTime()));

		// Depending on subscription size invocation could potentially require less than
		// one invocation. Keep track of 'partial' invocations until we can invoke.
		entitiesToProcess += processedPerSecond * getWorldDelta();
		if ( entitiesToProcess >= 1f ) {
			processEntities((int) entitiesToProcess, entities.getData(), entities.size());

			// keep remainder.
			entitiesToProcess-=(int)entitiesToProcess;
		}
	}

	protected float getWorldDelta() {
		return world.delta;
	}

	protected void processEntities(int entitiesToProcess, int[] entities, int size) {

		flyweight.id=-1;

		// process up to array size.
		int lastIndex = index + entitiesToProcess;
		for (int s = Math.min(size, lastIndex); s > index; index++) {
			flyweight.id = entities[index];
			process(flyweight);
		}

		if (lastIndex < size) {
			lastProcessedEntityId = flyweight.id;
			return;
		}

		// wrap around and process the rest.
		index = 0;
		lastIndex = lastIndex % size;
		for (int s = Math.min(size, lastIndex); s > index; index++) {
			flyweight.id = entities[index];
			process(flyweight);
		}

		// keep track of processed entity.
		lastProcessedEntityId = flyweight.id;
	}


	@Override
	protected void removed(int id) {

		// because subscriptions are always sorted by id,
		// we can assume smaller ids mean the index has shifted.
		if ( (id <= lastProcessedEntityId) && index > 0 ) {
			// re-align the index.
			index--;
		}
	}

	public float getRoundTripTime() {
		return roundTripTime;
	}

	public void setRoundTripTime(float roundTripTime) {
		if ( roundTripTime <= 0 ) throw new IllegalArgumentException("Round trip time must be >0.");
		this.roundTripTime = roundTripTime;
	}
}
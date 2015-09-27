package net.mostlyoriginal.api.system.core;

import com.artemis.Aspect;
import com.artemis.World;
import com.artemis.utils.IntBag;

/**
 * Use this when you need to test two sets of entities against each other.
 * <p/>
 * Warning: Involves nested operations O(n2), avoid large sets.
 *
 * @author Daan van Yperen
 */
public abstract class DualEntityProcessingSystem extends DualEntitySystem {

	/**
	 * Creates a new EntityProcessingSystem.
	 *
	 * @param aspectA to match set A
	 * @param aspectB to match set B
	 */
	public DualEntityProcessingSystem(Aspect.Builder aspectA, Aspect.Builder aspectB) {
		super(aspectA, aspectB);
	}

	@Override
	protected void setWorld(World world) {
		super.setWorld(world);
	}

	/**
	 * Process a entity this system is interested in.
	 *
	 * @param a the entity to process
	 * @param b the entity to process
	 */
	protected abstract void process(int a, int b);

	@Override
	protected final void processSystem() {
		final IntBag activesA = subscriptionA.getEntities();
		final IntBag activesB = subscriptionB.getEntities();

		final int[] arrayA = activesA.getData();
		final int[] arrayB = activesB.getData();

		for (int ia = 0, sa = activesA.size(); sa > ia; ia++) {
			for (int ib = 0, sb = activesB.size(); sb > ib; ib++) {
				process(arrayA[ia], arrayB[ib]);
			}

		}
	}
}

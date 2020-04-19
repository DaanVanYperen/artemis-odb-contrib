package net.mostlyoriginal.api.system.core;

import com.artemis.BaseSystem;

/**
 * System processed at a certain interval. Essentially the same as {@link com.artemis.systems.IntervalSystem} but with no entity processing.
 * 
 * @author Laurence Warne
 */
public abstract class BaseIntervalSystem extends BaseSystem {

	/** Accumulated delta to keep track of interval.*/
	protected float acc;
	/** How long to wait between updates.*/
	private final float interval;
	private float intervalDelta;

	/**
	 * Creates a new BaseIntervalSystem.
	 *
	 * @param interval the interval at which the system processes
	 */	
	public BaseIntervalSystem(float interval) {
		super();
		this.interval = interval;
	}

	@Override
	public boolean checkProcessing() {
		acc += getTimeDelta();
		if (acc >= interval) {
			acc -= interval;
			intervalDelta = (acc - intervalDelta);
			return true;
		}
		return false;	
	}

	/**
	 * Gets the actual delta since this system was last processed.
	 *
	 * @return Time passed since last process round.
	 */
	protected float getIntervalDelta() {
		return interval + intervalDelta;
	}

	protected float getTimeDelta() {
		return world.getDelta();
	}
}

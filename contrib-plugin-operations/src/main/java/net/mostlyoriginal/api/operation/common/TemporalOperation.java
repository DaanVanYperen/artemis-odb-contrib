package net.mostlyoriginal.api.operation.common;

import com.artemis.Entity;
import com.badlogic.gdx.math.Interpolation;

/**
 * @author Daan van Yperen
 */
public abstract class TemporalOperation extends Operation {

	protected float runtime;
	protected float duration;
	protected Interpolation interpolation = Interpolation.linear;

	protected boolean started;

	/** Called when operation is processed first. */
	protected void begin() {}
	/** Called after operation is called last. */
	protected void end() {}
	/** Called when the operation restarts. */

	@Override
	public void rewind() {
		runtime = 0;
	}

	@Override
	public final boolean process(float delta, Entity e) {
		if ( !started )
		{
			started = true;
			begin();
		}

		runtime += delta;
		if ( runtime > duration) runtime = duration;

		act( interpolation.apply(runtime / duration), e);

		if ( runtime >= duration)
		{
			completed=true;
			end();
		}

		return completed;
	}

	public abstract void act(float percentage, Entity e);

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	@Override
	public void reset() {
		super.reset();
		duration=0;
		runtime=0;
		interpolation=Interpolation.linear;
	}
}

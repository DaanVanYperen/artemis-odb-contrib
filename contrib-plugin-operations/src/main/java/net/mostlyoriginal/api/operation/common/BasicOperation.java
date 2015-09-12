package net.mostlyoriginal.api.operation.common;

import com.artemis.Entity;

/**
 * @author Daan van Yperen
 */
public abstract class BasicOperation extends Operation {

	public abstract void process(Entity e);

	@Override
	public final boolean process(float delta, Entity e)
	{
		if ( !completed ) {
			process(e);
			completed = true;
		}
		return true;
	}

}

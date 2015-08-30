package net.mostlyoriginal.api.component.graphics;

import net.mostlyoriginal.api.component.common.ExtendedComponent;

/**
 * Indicate this entity is renderable.
 * <p/>
 * Combine with specialized renderables, like Anim or Label.
 *
 * @author Daan van Yperen
 * @see Anim
 */
public class Renderable extends ExtendedComponent<Renderable> {

	/**
	 * target layer, higher is in front, lower is behind.
	 */
	public int layer = 0;

	public Renderable() {
	}
	public Renderable(int layer) {
		this.layer = layer;
	}

	@Override
	protected void reset() {
		layer = 0;
	}

	@Override
	public Renderable set(Renderable renderable) {
		layer = renderable.layer;
		return this;
	}
}

package net.mostlyoriginal.api.component.graphics;

import net.mostlyoriginal.api.component.common.ExtendedComponent;

/**
 * Indicate this entity requires rendering.
 * <p/>
 * Combine with specialized renderables, like Anim or Label.
 *
 * @author Daan van Yperen
 * @see Anim
 */
@Deprecated
public class Render extends ExtendedComponent<Render> {

	/**
	 * target layer, higher is in front, lower is behind.
	 */
	public int layer = 0;

	public Render() {}
	public Render(int layer) {
		this.layer = layer;
	}

	@Override
	protected void reset() {
		layer = 0;
	}

	@Override
	public void set(Render render) {
		layer = render.layer;
	}

	public void set(int layer) {
		this.layer = layer;
	}
}

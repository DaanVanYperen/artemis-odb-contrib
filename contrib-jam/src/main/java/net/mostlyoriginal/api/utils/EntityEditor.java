package net.mostlyoriginal.api.utils;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.EntityEdit;
import com.artemis.World;
import net.mostlyoriginal.api.component.basic.Angle;
import net.mostlyoriginal.api.component.basic.Bounds;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.basic.Scale;
import net.mostlyoriginal.api.component.graphics.Anim;
import net.mostlyoriginal.api.component.graphics.Renderable;
import net.mostlyoriginal.api.component.graphics.Tint;
import net.mostlyoriginal.api.component.physics.Physics;
import net.mostlyoriginal.api.component.ui.Font;
import net.mostlyoriginal.api.component.ui.Label;

/**
 * Helper for entity assembly.
 *
 * @author Daan van Yperen
 */
public abstract class EntityEditor<T extends EntityEditor> {

	private EntityEdit edit;
	private Entity entity;

	@SuppressWarnings("unchecked")
	protected T editEntity(Entity entity) {
		this.entity = entity;
		this.edit = entity.edit();
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	protected final T createEntity(World world) {
		editEntity(world.createEntity());
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public final T pos(float x, float y) {
		final Pos pos = add(Pos.class);
		pos.set(x, y);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public final T pos(int x, int y) {
		final Pos pos = add(Pos.class);
		pos.set(x, y);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public final T bounds(int minx, int miny, int maxx, int maxy) {
		final Bounds bounds = add(Bounds.class);
		bounds.minx = minx;
		bounds.miny = miny;
		bounds.maxx = maxx;
		bounds.maxy = maxy;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public final T anim(String id) {
		final Anim anim = add(Anim.class);
		anim.id = id;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	protected final <T extends Component> T add(Class<T> componentType) {
		return edit.create(componentType);
	}

	@SuppressWarnings("unchecked")
	public final T renderable(int layer) {
		final Renderable renderable = add(Renderable.class);
		renderable.layer = layer;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public final T angle(float rotation) {
		final Angle angle = add(Angle.class);
		angle.rotation = rotation;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public final T angle(int rotation, int ox, int oy) {
		final Angle angle = add(Angle.class);
		angle.rotation = rotation;
		angle.ox = ox;
		angle.oy = oy;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public Entity build() {
		final Entity tmp = this.entity;
		entity = null;
		edit = null;
		return tmp;
	}

	/**
	 * Add artemis managed components to entity.
	 */
	@SuppressWarnings("unchecked")
	public final T with(Class<? extends Component> component) {
		edit.create(component);
		return (T) this;
	}

	/**
	 * Add artemis managed components to entity.
	 */
	@SuppressWarnings("unchecked")
	public final T with(Class<? extends Component> component1, Class<? extends Component> component2) {
		edit.create(component1);
		edit.create(component2);
		return (T) this;
	}

	/**
	 * Add artemis managed components to entity.
	 */
	@SuppressWarnings("unchecked")
	public final T with(Class<? extends Component> component1, Class<? extends Component> component2, Class<? extends Component> component3) {
		edit.create(component1);
		edit.create(component2);
		edit.create(component3);
		return (T) this;
	}

	/**
	 * Add artemis managed components to entity.
	 */
	@SuppressWarnings("unchecked")
	public final T with(Class<? extends Component> component1, Class<? extends Component> component2, Class<? extends Component> component3, Class<? extends Component> component4) {
		edit.create(component1);
		edit.create(component2);
		edit.create(component3);
		edit.create(component4);
		return (T) this;
	}

	/**
	 * Add artemis managed components to entity.
	 */
	@SuppressWarnings("unchecked")
	public final T with(Class<? extends Component> component1, Class<? extends Component> component2, Class<? extends Component> component3, Class<? extends Component> component4, Class<? extends Component> component5) {
		edit.create(component1);
		edit.create(component2);
		edit.create(component3);
		edit.create(component4);
		edit.create(component5);
		return (T) this;
	}

	/**
	 * Add artemis managed components to entity.
	 */
	@SuppressWarnings("unchecked")
	public final T with(Class<? extends Component>... components) {
		for (int i = 0, n = components.length; i < n; i++) {
			edit.create(components[i]);
		}
		return (T) this;
	}


	@SuppressWarnings("unchecked")
	public final T physics() {
		add(Physics.class);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public final T tint(float r, float g, float b, float a) {
		Tint tint = add(Tint.class);
		tint.set(r, g, b, a);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T tint(String hex) {
		Tint tint = add(Tint.class);
		tint.setHex(hex);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T tint(Tint value) {
		Tint tint = add(Tint.class);
		tint.set(value);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T label(String text) {
		Label label = add(Label.class);
		label.text = text;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T font(String name) {
		Font font = add(Font.class);
		font.fontName = name;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public final T scale(float scale) {
		final Scale scaleComponent = add(Scale.class);
		scaleComponent.scale = scale;
		return (T) this;
	}

}

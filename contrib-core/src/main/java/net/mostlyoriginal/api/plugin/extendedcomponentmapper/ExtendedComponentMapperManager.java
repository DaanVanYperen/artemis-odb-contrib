package net.mostlyoriginal.api.plugin.extendedcomponentmapper;

import com.artemis.*;
import com.artemis.annotations.Wire;
import com.artemis.utils.Bag;

/**
 * Manages instances of component mappers.
 *
 * @author Daan van Yperen
 */
@Wire
public class ExtendedComponentMapperManager extends Manager {

	private Bag<M> mappers = new Bag<>(64);

	private ComponentManager componentManager;

	public <T extends Component> M<T> getFor(Class<T> type) {
		ComponentTypeFactory tf = world.getComponentManager().getTypeFactory();
		return getCreateMapper(tf.getTypeFor(type));
	}

	/** Fetch mapper, or create if missing. */
	@SuppressWarnings("unchecked")
	private <T extends Component> M<T> getCreateMapper(ComponentType type) {

		final int index = type.getIndex();

		mappers.ensureCapacity(index);
		M m = mappers.get(index);
		if (m == null) {
			m = setMapper(index, type.getType());
		}

		return m;
	}

	/** Set mapper at index. */
	private M setMapper(int index, Class<? extends Component> type) {
		M m = new M(type, world);
		mappers.set(index, m);
		return m;
	}
}

package net.mostlyoriginal.api.utils.builder;

/**
 * Artemis pieces with priority pending registration.
 *
 * @author Daan van Yperen
 * @see WorldConfigurationBuilder
 */
class Registerable<T> implements Comparable<Registerable<T>> {
	public final boolean passive;
	public final int priority;
	public final Class<?> itemType;
	public T item;

	public Registerable(T item, int priority, boolean passive) {
		this.item = item;
		itemType = item.getClass();
		this.priority = priority;
		this.passive = passive;
	}

	@Override
	public int compareTo(Registerable<T> o) {
		// Sort by priority descending.
		return o.priority - priority;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		return item.equals(((Registerable<?>) o).item);
	}

	@Override
	public int hashCode() {
		return item.hashCode();
	}

	/** create instance of Registerable. */
	public static <T> Registerable<T> of(T item) {
		return of(item, WorldConfigurationBuilder.Priority.NORMAL, false);
	}

	/** create instance of Registerable. */
	public static <T> Registerable<T> of(T item, int priority, boolean passive) {
		return new Registerable<>(item, priority, passive);
	}

	/** create instance of Registerable. */
	public static <T> Registerable<T> of(T item, int priority) {
		return of(item, priority, false);
	}
}

package net.mostlyoriginal.api.common;

import com.artemis.Component;

/**
 * @author Daan van Yperen
 */
public class Pokeable extends Component {
	public int id;
	public int pokes;

	public Pokeable() {
	}

	public Pokeable(int id) {
		this.id = id;
	}
}

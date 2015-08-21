package net.mostlyoriginal.api.plugin.extendedcomponentmapper;

import com.artemis.Component;

/**
 * @author Daan van Yperen
 */
public class Pos extends Component {
	int x;
	int y;

	public Pos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Pos() {
	}
}

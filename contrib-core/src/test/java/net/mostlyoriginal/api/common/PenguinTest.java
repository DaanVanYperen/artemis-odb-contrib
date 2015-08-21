package net.mostlyoriginal.api.common;

import com.artemis.BaseSystem;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import org.junit.Assert;

/**
 * @author Daan van Yperen
 */
public class PenguinTest {

	protected static final int INVALID_POKE_COUNT = 999;
	protected Penguin[] penguins;

	protected int getLastPokedIndex() {
		int highestPokes = 0;
		for (int i = 0; i < penguins.length; i++) {
			if (penguins[i].pokes < highestPokes) {
				return i - 1;
			}
			highestPokes = penguins[i].pokes;
		}
		return -1;
	}

	protected World bakeWorld(BaseSystem system) {
		return new World(new WorldConfiguration().setSystem(system));
	}

	protected void assertAllPenguinsPoked(int count) {
		for (int i=0;i<10;i++ ) {
			Assert.assertEquals("Penguin #" + i + " wrong pokes.", count, penguins[i].pokes);
		}
	}
}

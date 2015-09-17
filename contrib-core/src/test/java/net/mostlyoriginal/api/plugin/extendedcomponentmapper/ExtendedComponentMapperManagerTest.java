package net.mostlyoriginal.api.plugin.extendedcomponentmapper;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Daan van Yperen
 */
public class ExtendedComponentMapperManagerTest {

	private World w;
	private M<Pos> mPos;

	@Before
	public void setup() {
		w = new World(new WorldConfigurationBuilder().dependsOn(ExtendedComponentMapperPlugin.class).build());
		mPos = M.getFor(Pos.class, w);
	}

	@Test
	public void ensure_resolved_mapper_isnt_duplicated() {
		M<Pos> posMapperAgain = M.getFor(Pos.class, w);
		Assert.assertTrue(mPos == posMapperAgain);
	}

	@Test
	public void ensure_resolved_mapper_actually_works() {
		Entity entity = w.createEntity();

		Pos pos = entity.edit().create(Pos.class);

		Assert.assertEquals(mPos.get(entity),pos);
	}
}
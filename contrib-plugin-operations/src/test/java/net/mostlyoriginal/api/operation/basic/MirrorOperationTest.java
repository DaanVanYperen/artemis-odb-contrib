package net.mostlyoriginal.api.operation.basic;

import com.artemis.Entity;
import com.artemis.World;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.ExtendedComponentMapperPlugin;
import net.mostlyoriginal.api.utils.builder.WorldConfigurationBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Daan van Yperen
 */
public class MirrorOperationTest {

	private Entity entity;
	private MirrorableTestComponent component;
	private MirrorOperation mirrorOperation;

	@Test
	public void test_process_adds_missing_component() {
		mirrorOperation.setup(new MirrorableTestComponent(5));
		mirrorOperation.process(0, entity);

		Assert.assertEquals(5, entity.getComponent(MirrorableTestComponent.class).val);
	}

	@Test
	public void test_process_updates_existing_component() {

		MirrorableTestComponent component = entity.edit().create(MirrorableTestComponent.class);

		mirrorOperation.setup(new MirrorableTestComponent(5));
		mirrorOperation.process(0, entity);

		Assert.assertEquals(5, component.val);
	}

	@Before
	public void setup() {
		World world = new World(new WorldConfigurationBuilder().dependsOn(ExtendedComponentMapperPlugin.class).build());
		entity = world.createEntity();
		component = new MirrorableTestComponent(0);
		mirrorOperation = new MirrorOperation();
	}
}
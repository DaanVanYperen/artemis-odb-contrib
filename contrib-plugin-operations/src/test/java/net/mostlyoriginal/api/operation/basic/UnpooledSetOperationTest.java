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
public class UnpooledSetOperationTest {

	private Entity entity;
	private UnpooledMirrorOperation unpooledMirrorOperation;

	@Test
	public void test_process_adds_missing_component() {
		unpooledMirrorOperation.setup(new MirrorableTestComponent(5));
		unpooledMirrorOperation.process(0, entity);

		Assert.assertEquals(5, entity.getComponent(MirrorableTestComponent.class).val);
	}

	@Test
	public void test_process_updates_existing_component() {

		MirrorableTestComponent component = entity.edit().create(MirrorableTestComponent.class);

		unpooledMirrorOperation.setup(new MirrorableTestComponent(5));
		unpooledMirrorOperation.process(0, entity);

		Assert.assertEquals(5, component.val);
	}

	@Before
	public void setup() {
		World world = new World(new WorldConfigurationBuilder().dependsOn(ExtendedComponentMapperPlugin.class).build());
		entity = world.createEntity();
		unpooledMirrorOperation = new UnpooledMirrorOperation();
	}
}
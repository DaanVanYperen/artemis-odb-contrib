package net.mostlyoriginal.api.operation;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Interpolation;
import net.mostlyoriginal.api.operation.act.TweenOperation;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.ExtendedComponentMapperPlugin;
import net.mostlyoriginal.api.utils.builder.WorldConfigurationBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Daan van Yperen
 */
public class TweenOperationTest {

	public static final float FLOAT_MAX_DELTA = 0.02f;
	private Entity entity;
	private TweenableTestComponent component;

	@Test
	public void ensure_tweening_at_start_time_matches_starting_tween() {
		TweenOperation step = new TweenOperation();
		step.setup(new TweenableTestComponent(-10), new TweenableTestComponent(10), Interpolation.linear, 1f);
		step.process(0, entity);
		Assert.assertEquals(-10f, component.val,  FLOAT_MAX_DELTA);
	}

	@Test
	public void ensure_tweening_at_end_time_matches_ending_tween() {
		TweenOperation step = new TweenOperation();
		step.setup(new TweenableTestComponent(-10), new TweenableTestComponent(10), Interpolation.linear, 1f);
		step.process(1f,entity);
		Assert.assertEquals(10f, component.val,  FLOAT_MAX_DELTA);
	}

	@Test(expected = IllegalArgumentException.class)
	public void illegal_duration_exception() {
		TweenOperation step = new TweenOperation();
		step.setup(new TweenableTestComponent(0), new TweenableTestComponent(0), Interpolation.linear, 0);
	}

	@Before
	public void setup() {
		World world = new World(new WorldConfigurationBuilder().dependsOn(ExtendedComponentMapperPlugin.class).build());
		entity = world.createEntity();
		component = new TweenableTestComponent(5);
		entity.edit().add(component);
	}

}
package net.mostlyoriginal.api.operation.temporal;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Interpolation;
import net.mostlyoriginal.api.operation.TweenableTestComponent;
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
	private TweenOperation<TweenableTestComponent> step;

	@Test
	public void ensure_tweening_at_start_time_matches_starting_tween() {
		step.setup(Interpolation.linear, 1f);
		step.getFrom().val=-10;
		step.getTo().val=10;
		step.process(0, entity);
		Assert.assertEquals(-10f, component.val, FLOAT_MAX_DELTA);
	}

	@Test
	public void ensure_tweening_at_end_time_matches_ending_tween() {
		step.setup(Interpolation.linear, 1f);
		step.getFrom().val=-10;
		step.getTo().val=10;
		step.process(1f,entity);
		Assert.assertEquals(10f, component.val,  FLOAT_MAX_DELTA);
	}

	@Test(expected = IllegalArgumentException.class)
	public void illegal_duration_exception() {
		step.setup(Interpolation.linear, 0);
	}

	@Before
	public void setup() {
		World world = new World(new WorldConfigurationBuilder().dependsOn(ExtendedComponentMapperPlugin.class).build());
		entity = world.createEntity();
		component = new TweenableTestComponent(5);
		entity.edit().add(component);
		step= new TweenableTestOperation();
	}


	private static class TweenableTestOperation extends TweenOperation<TweenableTestComponent> {

		public TweenableTestOperation() {
			super(TweenableTestComponent.class);
		}
	}
}
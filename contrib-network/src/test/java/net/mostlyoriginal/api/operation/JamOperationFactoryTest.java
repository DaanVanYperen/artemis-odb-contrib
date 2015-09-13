package net.mostlyoriginal.api.operation;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.utils.Duration;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Daan van Yperen
 */
public class JamOperationFactoryTest {

	@Test
	public void valid_tweenPos_xy_setup_properly() {
		TweenPosOperation tweenPos = JamOperationFactory.moveBetween(0, 1, 2, 3, Duration.seconds(1));
		assertTweenPosProperlySet(tweenPos);
	}

	@Test
	public void valid_tweenPos_pos_setup_properly() {
		TweenPosOperation tweenPos = JamOperationFactory.moveBetween(new Pos(0, 1), new Pos(2, 3), Duration.seconds(1));
		assertTweenPosProperlySet(tweenPos);
	}

	@Test
	public void valid_tweenPos_vector2_setup_properly() {
		TweenPosOperation tweenPos = JamOperationFactory.moveBetween(new Vector2(0, 1), new Vector2(2, 3), Duration.seconds(1));
		assertTweenPosProperlySet(tweenPos);
	}


	@Test
	public void valid_tweenPos_xy_custom_interpolate_setup_properly() {
		TweenPosOperation tweenPos = JamOperationFactory.moveBetween(0, 1, 2, 3, Duration.seconds(1), Interpolation.bounce);
		assertTweenPosProperlySet(tweenPos);
	}

	@Test
	public void valid_tweenPos_pos_custom_interpolate_setup_properly() {
		TweenPosOperation tweenPos = JamOperationFactory.moveBetween(new Pos(0, 1), new Pos(2, 3), Duration.seconds(1), Interpolation.bounce);
		assertTweenPosProperlySet(tweenPos);
	}

	@Test
	public void valid_tweenPos_vector2_custom_interpolate_setup_properly() {
		TweenPosOperation tweenPos = JamOperationFactory.moveBetween(new Vector2(0, 1), new Vector2(2, 3), Duration.seconds(1), Interpolation.bounce);
		assertTweenPosProperlySet(tweenPos);
	}

	protected void assertTweenPosProperlySet(TweenPosOperation tweenPos) {
		Assert.assertEquals(0f, tweenPos.getFrom().xy.x, 0.001f);
		Assert.assertEquals(1f,tweenPos.getFrom().xy.y,0.001f);
		Assert.assertEquals(2f,tweenPos.getTo().xy.x,0.001f);
		Assert.assertEquals(3f, tweenPos.getTo().xy.y, 0.001f);
	}

	@Test
	public void valid_moveTo_xy_setup_properly() {
		assertValidPos(JamOperationFactory.moveTo(0,1));
	}

	@Test
	public void valid_moveTo_pos_setup_properly() {
		assertValidPos(JamOperationFactory.moveTo(new Pos(0,1)));
	}

	@Test
	public void valid_moveTo_vector2_setup_properly() {
		assertValidPos(JamOperationFactory.moveTo(new Vector2(0,1)));
	}

	protected void assertValidPos(SetPosOperation pos) {
		Assert.assertEquals(0f, pos.get().xy.x, 0.001f);
		Assert.assertEquals(1f, pos.get().xy.y, 0.001f);
	}

}
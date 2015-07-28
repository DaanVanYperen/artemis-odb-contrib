package net.mostlyoriginal.api.screen.core;

import com.artemis.World;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;

/**
 * @author Daan van Yperen
 */
public abstract class WorldScreen implements Screen {

	public static final float MIN_DELTA = 1 / 15f;
	protected World world;

	public WorldScreen() {
	}

	protected abstract World createWorld();

	@Override
	public void show() {
		if ( world == null )
		{
			world = createWorld();
		}
	}

	@Override
	public void render(float delta) {
		if ( world == null ) {
			throw new RuntimeException("World not initialized.");
		}
		// Prevent spikes in delta from causing insane world updates.
		world.setDelta(MathUtils.clamp(delta, 0, MIN_DELTA));
		world.process();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

}

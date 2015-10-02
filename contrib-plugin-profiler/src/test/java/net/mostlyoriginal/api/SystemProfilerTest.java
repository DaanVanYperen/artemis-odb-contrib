package net.mostlyoriginal.api;

import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import net.mostlyoriginal.plugin.profiler.ProfilerSystem;
import net.mostlyoriginal.plugin.profiler.SystemProfiler;

/**
 * Basic integration test for {@link SystemProfiler}
 * @author piotr-j
 */
public class SystemProfilerTest extends Game {

	World world;

	@Override public void create () {
		world = new World(new WorldConfigurationBuilder()
			.with(new ProfilerSystem())
			.build());
	}

	@Override public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.delta = Gdx.graphics.getDeltaTime();
		world.process();
	}

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 600;
		config.useHDPI = true;
		new LwjglApplication(new SystemProfilerTest(), config);
	}
}

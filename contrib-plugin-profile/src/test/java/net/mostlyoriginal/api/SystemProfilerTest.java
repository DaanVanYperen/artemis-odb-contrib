package net.mostlyoriginal.api;

import com.artemis.BaseSystem;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import net.mostlyoriginal.api.utils.builder.WorldConfigurationBuilder;

/**
 * Basic integration test for {@link SystemProfiler}
 * @author piotr-j
 */
public class SystemProfilerTest extends Game {
	OrthographicCamera camera;
	ShapeRenderer renderer;
	Stage stage;
	Skin skin;

	World world;
	@Override public void create () {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(false);
		camera.update();
		renderer = new ShapeRenderer();
		stage = new Stage();
		stage.getBatch().setProjectionMatrix(camera.combined);
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.classpath("uiskin.json"));

		WorldConfiguration config = new WorldConfigurationBuilder()
			.with(new ProfilerSystem())
			.build();
		config.register(camera).register(renderer).register(stage).register(skin);
		world = new World(config);
		// all systems will get a profiler
		world.setInvocationStrategy(new ProfilerInvocationStrategy(world));
	}

	@Wire
	public static class ProfilerSystem extends BaseSystem {
		@Wire OrthographicCamera camera;
		@Wire ShapeRenderer renderer;
		@Wire Stage stage;
		@Wire Skin skin;

		SystemProfilerGUI gui;

		@Override protected void initialize () {
			// resume profiling
			SystemProfiler.resume();
			// setup some static config like colors etc
			SystemProfilerGUI.GRAPH_H_LINE.set(Color.ORANGE);
			gui = new SystemProfilerGUI(skin, "default");
			gui.setResizeBorder(8);
			gui.show(stage);
			gui.setWidth(Gdx.graphics.getWidth());
		}

		@Override protected void processSystem () {
			if (!isEnabled() || gui.getParent() == null) {
				return;
			}
			stage.act(world.delta);
			stage.draw();
			renderer.setProjectionMatrix(camera.combined);
			renderer.begin(ShapeRenderer.ShapeType.Line);
			gui.updateAndRender(world.delta, renderer);
			renderer.end();
		}

		@Override protected void dispose () {
			SystemProfiler.dispose();
		}
	}

	@Override public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.delta = Gdx.graphics.getDeltaTime();
		world.process();
	}

	@Override public void dispose () {
		world.dispose();
		stage.dispose();
		renderer.dispose();
		skin.dispose();
	}

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 600;
		config.useHDPI = true;
		new LwjglApplication(new SystemProfilerTest(), config);
	}
}

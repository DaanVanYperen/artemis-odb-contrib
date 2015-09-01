package net.mostlyoriginal.plugin;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import net.mostlyoriginal.api.SystemProfiler;
import net.mostlyoriginal.api.SystemProfilerGUI;

/**
 * Renders profiler.
 *
 * @author piotr-j
 * @author Daan van Yperen
 */
@Wire
public class ProfilerSystem extends BaseSystem {

	OrthographicCamera camera;
	ShapeRenderer renderer;
	Stage stage;
	Skin skin;

	SystemProfilerGUI gui;

	@Override
	protected void initialize() {

		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(false);
		camera.update();
		renderer = new ShapeRenderer();
		stage = new Stage();
		stage.getBatch().setProjectionMatrix(camera.combined);
		skin = new Skin(Gdx.files.internal("profiler/uiskin.json"));

		// resume profiling
		SystemProfiler.resume();
		// setup some static config like colors etc
		SystemProfilerGUI.GRAPH_H_LINE.set(Color.ORANGE);
		gui = new SystemProfilerGUI(skin, "default");
		gui.setResizeBorder(8);
		gui.show(stage);
		gui.setWidth(Gdx.graphics.getWidth());
	}

	@Override
	protected void processSystem() {
		if (!isEnabled() || gui.getParent() == null) {
			return;
		}

		updateStageInput();
		stage.act(world.delta);
		stage.draw();
		renderer.setProjectionMatrix(camera.combined);
		renderer.begin(ShapeRenderer.ShapeType.Line);
		gui.updateAndRender(world.delta, renderer);
		renderer.end();
	}

	private boolean leftMouseDown;
	/** Emulate stage input to maintain pre-existing input processor. */
	private void updateStageInput() {
		if ( Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			if ( !leftMouseDown) {
				leftMouseDown = true;
				stage.touchDown(Gdx.input.getX(), Gdx.input.getY(), 0, Input.Buttons.LEFT);
			} else {
				stage.touchDragged(Gdx.input.getX(), Gdx.input.getY(), 0);
			}
		} else if (leftMouseDown)  {
			leftMouseDown = false;
			stage.touchUp(Gdx.input.getX(), Gdx.input.getY(), 0, Input.Buttons.LEFT);
		}
	}

	@Override
	protected void dispose() {
		SystemProfiler.dispose();
	}
}

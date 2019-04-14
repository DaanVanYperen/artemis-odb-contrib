package net.mostlyoriginal.api.utils.quadtree;

import com.artemis.*;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.EntityBuilder;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import net.mostlyoriginal.api.utils.QuadTree;

/**
 * Integration test for {@link QuadTree}
 *
 * @author Piotr-J
 */
public class QuadTreeIntegrationTest extends Game {
    OrthographicCamera camera;
    ShapeRenderer renderer;

    World world;

    @Override
    public void create() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false);
        camera.update();
        renderer = new ShapeRenderer();

        WorldConfiguration config = new WorldConfigurationBuilder()
                .with(new GodSystem())
                .with(new QuadTreeSystem())
                .build();
        config.register(camera).register(renderer);
        world = new World(config);

        for (int i = 0; i < 10000; i++) {
            new EntityBuilder(world).with(
                    new Position(MathUtils.random(Gdx.graphics.getWidth() - 25), MathUtils.random(Gdx.graphics.getHeight() - 25)),
                    new Velocity(MathUtils.random(-50, 50), MathUtils.random(-50, 50)),
                    new Bounds(MathUtils.random(3, 15), MathUtils.random(3, 15))
            ).build();
        }
    }

    @Wire
    public static class QuadTreeSystem extends IteratingSystem {
        private ComponentMapper<Bounds> mBounds;
        QuadTree qt;

        public QuadTreeSystem() {
            super(Aspect.all());
        }

        @Override
        protected void initialize() {
            // pixels, bad but simple
            qt = new QuadTree(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        @Override
        protected void inserted(int entityId) {
            Rectangle b = mBounds.get(entityId).rect;
            qt.insert(entityId, b.x, b.y, b.width, b.height);
        }

        @Override
        protected void process(int entityId) {
            Rectangle b = mBounds.get(entityId).rect;
            qt.update(entityId, b.x, b.y, b.width, b.height);
        }

        @Override
        protected void removed(int entityId) {
            qt.remove(entityId);
        }

        public void get(IntBag fill, Rectangle r) {
            qt.getExact(fill, r.x, r.y, r.width, r.height);
        }
    }

    public static class Position extends Component {
        public Vector2 pos = new Vector2();

        public Position() {
        }

        public Position(float x, float y) {
            pos.set(x, y);
        }
    }

    public static class Velocity extends Component {
        public Vector2 vel = new Vector2();

        public Velocity() {
        }

        public Velocity(float x, float y) {
            vel.set(x, y);
        }
    }

    public static class Bounds extends Component {
        public Rectangle rect = new Rectangle();

        public Bounds() {
        }

        public Bounds(float w, float h) {
            rect.setSize(w, h);
        }
    }

    public static class Selected extends Component {
    }

    /**
     * This is a poor system because it is doing way too much, but it makes test concise
     */
    @Wire
    public static class GodSystem extends EntityProcessingSystem {
        @Wire
        OrthographicCamera camera;
        @Wire
        ShapeRenderer renderer;
        private ComponentMapper<Position> mPosition;
        private ComponentMapper<Velocity> mVelocity;
        private ComponentMapper<Bounds> mBounds;
        private ComponentMapper<Selected> mSelected;

        @Wire
        QuadTreeSystem qt;

        public GodSystem() {
            super(Aspect.all(Position.class, Bounds.class));
        }

        float timer = 1;
        Rectangle test = new Rectangle();
        IntBag fill = new IntBag();

        @Override
        protected void begin() {
            timer += world.delta;
            if (timer > 1) {
                timer -= 1;
                test.set(
                        MathUtils.random(Gdx.graphics.getWidth() - 50), MathUtils.random(Gdx.graphics.getHeight() - 50),
                        MathUtils.random(10f, 100f), MathUtils.random(10f, 100f)
                );
            }
            fill.clear();
            qt.get(fill, test);
            for (int i = 0; i < fill.size(); i++) {
                // since we modify and test in same system this is delayed 1 frame
                world.getEntity(fill.get(i)).edit().create(Selected.class);
            }
            renderer.setProjectionMatrix(camera.combined);
            renderer.begin(ShapeRenderer.ShapeType.Line);
        }

        Vector2 tmp = new Vector2();

        @Override
        protected void process(Entity e) {
            Vector2 pos = mPosition.get(e).pos;
            Vector2 vel = mVelocity.get(e).vel;
            Rectangle bounds = mBounds.get(e).rect;
            pos.add(tmp.set(vel).scl(world.delta));
            if (pos.x < 0) {
                pos.x = 0;
                vel.x *= -1;
            } else if (pos.x + bounds.width > Gdx.graphics.getWidth()) {
                pos.x = Gdx.graphics.getWidth() - bounds.width;
                vel.x *= -1;
            }

            if (pos.y < 0) {
                pos.y = 0;
                vel.y *= -1;
            } else if (pos.y + bounds.height > Gdx.graphics.getHeight()) {
                pos.y = Gdx.graphics.getHeight() - bounds.height;
                vel.y *= -1;
            }
            bounds.setPosition(pos.x, pos.y);
            if (mSelected.has(e)) {
                e.edit().remove(Selected.class);
                renderer.setColor(Color.RED);
            } else {
                renderer.setColor(Color.MAROON);
            }
            renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        }

        @Override
        protected void end() {
            renderer.setColor(Color.CYAN);
            renderer.rect(test.x, test.y, test.width, test.height);
            renderer.end();
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.delta = Gdx.graphics.getDeltaTime();
        world.process();
    }

    @Override
    public void dispose() {
        world.dispose();
        renderer.dispose();
    }

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;
        config.useHDPI = true;
        config.resizable = false;
        new LwjglApplication(new QuadTreeIntegrationTest(), config);
    }
}

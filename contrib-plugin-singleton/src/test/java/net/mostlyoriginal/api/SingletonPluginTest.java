package net.mostlyoriginal.api;

import org.junit.Test;

import com.artemis.BaseSystem;
import com.artemis.Component;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;

@SuppressWarnings("unused")
public class SingletonPluginTest {

    @Test(expected = Test.None.class)
    public void testNonComponentSingleton() {
        WorldConfiguration config = new WorldConfigurationBuilder()
                .with(new SingletonPlugin())
                .with(new NoComponentSystem())
                .build();

        new World(config);
    }

    @Test(expected = Test.None.class)
    public void testComponentSingleton() {
        WorldConfiguration config = new WorldConfigurationBuilder()
                .with(new SingletonPlugin())
                .with(new SomeComponentSystem())
                .build();

        new World(config);
    }
    
    @Test(expected = SingletonException.class)
    public void testStrictModeImplicit() {
        WorldConfiguration config = new WorldConfigurationBuilder()
                .with(new SingletonPlugin())
                .with(new SomeComponentSystem())
                .build();

        World world = new World(config);
        int entity = world.create();
        world.getMapper(SomeComponent.class).create(entity);
        world.process();
    }
    
    @Test(expected = SingletonException.class)
    public void testStrictModeExplicit() {
        WorldConfiguration config = new WorldConfigurationBuilder()
                .with(new SingletonPlugin(true))
                .with(new SomeComponentSystem())
                .build();

        World world = new World(config);
        int entity = world.create();
        world.getMapper(SomeComponent.class).create(entity);
        world.process();
    }

    @Test(expected = Test.None.class)
    public void testNonStrictModeExplicit() {
        WorldConfiguration config = new WorldConfigurationBuilder()
                .with(new SingletonPlugin(false))
                .with(new SomeComponentSystem())
                .build();

        World world = new World(config);
        int entity = world.create();
        world.getMapper(SomeComponent.class).create(entity);
        world.process();
    }

    public static class SomeComponentSystem extends BaseSystem {
        private SomeComponent someComponent;
        
        @Override
        protected void initialize() {
            if (someComponent == null) {
                throw new IllegalStateException("singleton not injected");
            }
        }

        @Override
        protected void processSystem() {
        }
    }

    public static class NoComponentSystem extends BaseSystem {
        private NoComponent noComponent;
        
        @Override
        protected void initialize() {
            if (noComponent != null) {
                throw new IllegalStateException("non-component singleton unexpectedly injected");
            }
        }

        @Override
        protected void processSystem() {
        }
    }

    @Singleton
    public static class SomeComponent extends Component {
    }

    @Singleton
    public static class NoComponent {
    }

}

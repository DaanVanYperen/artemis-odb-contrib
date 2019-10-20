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

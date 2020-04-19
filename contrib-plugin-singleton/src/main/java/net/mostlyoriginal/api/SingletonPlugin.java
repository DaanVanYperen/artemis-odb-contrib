package net.mostlyoriginal.api;

import com.artemis.*;
import com.artemis.EntitySubscription.SubscriptionListener;
import com.artemis.annotations.UnstableApi;
import com.artemis.injection.FieldResolver;
import com.artemis.utils.IntBag;
import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.Field;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static com.artemis.utils.reflect.ClassReflection.isAnnotationPresent;

/**
 * Dependency injection for singleton components. Creates a singleton component upon injection.
 * 
 * <p>
 * Takes full responsibility for singleton component lifecycle; do not manage annotated
 * singleton components yourself.</p>
 * 
 * <p>
 * By default the plugin runs in strict mode, throwing a {@link SingletonException} 
 * when an entity enters the world with a {@link Singleton @Singleton} component.</p>
 * 
 * <p>This behaviour can be disabled using {@link #SingletonPlugin(boolean) new SingletonPlugin(false)}.</p>
 *
 * @see Singleton
 * @see SingletonException
 * 
 * @author Daan van Yperen
 */
@UnstableApi
public class SingletonPlugin implements ArtemisPlugin {

    private final boolean strict;

    /**
     * Creates the SingletonPlugin in strict mode, throwing a {@link SingletonException} 
     * when an entity enters the world with a {@link Singleton @Singleton} component.
     */
    public SingletonPlugin() {
        this(true);
    }

    /**
     * Creates the SingletonPlugin.
     * 
     * @param strict enables strict mode, see {@link #SingletonPlugin()}
     */
    public SingletonPlugin(boolean strict) {
        this.strict = strict;
    }

    @Override
    public void setup(WorldConfigurationBuilder b) {
        SingletonFieldResolver singletonResolver = new SingletonFieldResolver();
        b.register(singletonResolver);

        if (strict) {
            b.with(new SingletonValidationSystem(singletonResolver));
        }
    }

    /**
     * Resolves singleton fields in systems.
     */
    public static class SingletonFieldResolver implements FieldResolver {

        private HashMap<Class<? extends Component>, Component> cachedSingletons;
        private EntityEdit singletonContainerEntity;

        @Override
        public void initialize(World world) {
            // we retain some state which should be fine as long as we're bound to the same world.
            this.cachedSingletons = new HashMap<>();
            this.singletonContainerEntity = world.createEntity().edit();
        }

        @Override
        public Object resolve(Object target, Class<?> fieldType, Field field) {
            if (isAnnotationPresent(fieldType, Singleton.class) && ClassReflection.isAssignableFrom(Component.class, fieldType)) {
                return getCreateSingletonComponent((Class<Component>) fieldType);
            }
            return null;
        }

        private Component getCreateSingletonComponent(Class<Component> component) {
            if (!cachedSingletons.containsKey(component)) {
                cachedSingletons.put(component, singletonContainerEntity.create(component));
            }
            return cachedSingletons.get(component);
        }
    }

    public static class SingletonValidationSystem extends BaseSystem implements SubscriptionListener {

        private final SingletonFieldResolver singletonResolver;
        private final Set<Class<? extends Component>> singletonComponents;

        private AspectSubscriptionManager asm;

        public SingletonValidationSystem(SingletonFieldResolver singletonResolver) {
            this.singletonResolver = singletonResolver;
            this.singletonComponents = new HashSet<>();
        }

        @Override
        protected void initialize() {
            setEnabled(false);

            if (singletonResolver.cachedSingletons.size() > 0) {
                this.singletonComponents.addAll(singletonResolver.cachedSingletons.keySet());
                asm.get(Aspect.one(singletonComponents)).addSubscriptionListener(this);
            }
        }

        @Override
        protected void processSystem() {
        }

        @Override
        public void inserted(IntBag entities) {
            throw new SingletonException(singletonComponents);
        }

        @Override
        public void removed(IntBag entities) {
        }

    }

}

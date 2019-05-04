package net.mostlyoriginal.api;

import com.artemis.*;
import com.artemis.annotations.UnstableApi;
import com.artemis.injection.FieldResolver;
import com.artemis.utils.reflect.Field;

import java.util.HashMap;

import static com.artemis.utils.reflect.ClassReflection.isAnnotationPresent;

/**
 * Dependency injection for singleton components. Creates a singleton component upon injection.
 * <p>
 * Takes full responsibility for singleton component lifecycle; do not manage annotated
 * singleton components yourself.
 *
 * @see Singleton
 * @author Daan van Yperen
 */
@UnstableApi
public class SingletonPlugin implements ArtemisPlugin {
    @Override
    public void setup(WorldConfigurationBuilder b) {
        b.register(new SingletonFieldResolver());
    }

    /**
     * Resolves singleton fields in systems.
     */
    private static class SingletonFieldResolver implements FieldResolver {

        private HashMap<Class<?>, Component> cachedSingletons;
        private EntityEdit singletonContainerEntity;

        @Override
        public void initialize(World world) {
            // we retain some state which should be fine as long as we're bound to the same world.
            this.cachedSingletons = new HashMap<>();
            this.singletonContainerEntity = world.createEntity().edit();
        }

        @Override
        public Object resolve(Object target, Class<?> fieldType, Field field) {
            if (isAnnotationPresent(fieldType, Singleton.class)) {
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
}

package net.mostlyoriginal.plugin;

import com.artemis.ArtemisPlugin;
import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.injection.FieldResolver;
import com.artemis.utils.reflect.Field;
import net.mostlyoriginal.plugin.querydsl.Query;

/**
 * Convenience plugin for finding entities with a stream like DSL.
 * <p>
 * Register plugin with builder and add {@see Query} as an uninitialized field to your systems to start using.
 * <p>
 * {@code WorldConfigurationBuilder().dependsOn(QueryDslPlugin.class); }
 *
 * @author Daan van Yperen
 */
public class QueryDslPlugin implements ArtemisPlugin {

    @Override
    public void setup(WorldConfigurationBuilder b) {
        b.register(new QueryFieldResolver());
    }

    /**
     * Provides new instances of query class.
     */
    private static class QueryFieldResolver implements FieldResolver {
        private World world;

        @Override
        public void initialize(World world) {
            this.world = world;
        }

        @Override
        public Object resolve(Object target, Class<?> fieldType, Field field) {
            if (Query.class.equals(fieldType)) return new Query(world);
            return null;
        }
    }
}
package net.mostlyoriginal.api;

import com.artemis.ArtemisPlugin;
import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.injection.FieldResolver;
import com.artemis.utils.reflect.Field;

/**
 * Provides logging to systems.
 *
 * When writing a plugin that requires logging, don't dependOn a specific
 * implementation, instead dependOn this LoggingPlugin so users can pick
 * their preferred logging implementation (or roll their own).
 *
 * Implement this class to provide your own logging.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.plugin.LibgdxLoggingPlugin
 * @since 3.0.0
 */
public abstract class LoggingPlugin implements ArtemisPlugin {

    @Override
    public void setup(WorldConfigurationBuilder b) {
        b.register(new FieldResolver() {
            @Override
            public void initialize(World world) {
            }

            @Override
            public Object resolve(Object target, Class<?> fieldType, Field field) {
                if (fieldType.equals(Log.class)) {
                    return createLogger(target);
                }
                return null;
            }
        });
    }

    protected abstract Object createLogger(Object target);
}
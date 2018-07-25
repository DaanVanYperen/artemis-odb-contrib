package net.mostlyoriginal.plugin;

import com.badlogic.gdx.Gdx;
import net.mostlyoriginal.api.Log;
import net.mostlyoriginal.api.LoggingPlugin;

import java.util.logging.Level;

import static com.badlogic.gdx.Application.LOG_DEBUG;
import static com.badlogic.gdx.Application.LOG_ERROR;
import static com.badlogic.gdx.Application.LOG_INFO;

/**
 * Provides logging via LibGDX.
 * <p>
 * Instructions:
 * 1. Add {@see net.mostlyoriginal.api.Log} to your systems for logging.
 *
 * @author Daan van Yperen
 * @since 3.0.0
 */
public class LibgdxLoggingPlugin extends LoggingPlugin {

    @Override
    protected Object createLogger(Object target) {
        return new LibgdxLogImpl(target.getClass().getSimpleName());
    }

    private static class LibgdxLogImpl implements Log {

        private final String tag;

        public LibgdxLogImpl(String tag) {
            this.tag = tag;
        }

        @Override
        public void info(String message) {
            Gdx.app.log(tag, message);
        }

        @Override
        public void info(String message, Object... args) {
            info(String.format(message, args));
        }

        @Override
        public void error(String message) {
            Gdx.app.log(tag, message);
        }

        @Override
        public void error(String message, Object... args) {
            error(String.format(message, args));
        }

        @Override
        public void debug(String message) {
            Gdx.app.debug(tag, message);
        }

        @Override
        public void debug(String message, Object... args) {
            debug(String.format(message, args));
        }

        @Override
        public boolean isInfoEnabled() {
            return Gdx.app.getLogLevel() <= LOG_INFO;
        }

        @Override
        public boolean isErrorEnabled() {
            return Gdx.app.getLogLevel() <= LOG_ERROR;
        }

        @Override
        public boolean isDebugEnabled() {
            return Gdx.app.getLogLevel() <= LOG_DEBUG;
        }
    }

}

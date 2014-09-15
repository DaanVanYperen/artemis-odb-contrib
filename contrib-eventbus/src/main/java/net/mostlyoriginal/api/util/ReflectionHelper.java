package net.mostlyoriginal.api.util;

import com.artemis.utils.reflect.Method;

/**
 * Method Reflection helper.
 *
 * Centralized location for all our to be expected GWT pain.
 *
 * @author Daan van Yperen
 */
public abstract class ReflectionHelper {

    /** Get parameter type of method first parameter, or null if none. */
    public static Class getFirstParameterType(Method method) {
        if ( method == null ) throw new NullPointerException("Method argument required.");
        final Class<?>[] types = method.getParameterTypes();
        return types.length != 0 ? types[0] : null;
    }
}

package net.mostlyoriginal.api.utils;

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
        return getParameterType(method, 0);
    }

    /** Get parameter type of method a parameter, or null if none. */
    public static Class getParameterType(Method method, int index) {
        if ( method == null ) throw new NullPointerException("Method argument required.");
        final Class<?>[] types = method.getParameterTypes();
        return types.length > index ? types[index] : null;
    }
}

package net.mostlyoriginal.api.event;

import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.Method;
import net.mostlyoriginal.api.util.ReflectionHelper;

/**
 * @author Daan van Yperen
 * @todo GWT provide method support.
 */
public class EventListener {

    protected final Object object;
    protected final Method method;

    /**
     * @param object
     * @param method
     */
    public EventListener(Object object, Method method) {
        if (object == null) throw new NullPointerException("Object cannot be null.");
        if (method == null) throw new NullPointerException("Method cannot be null.");
        method.setAccessible(true);
        if ( method.getParameterTypes().length != 1 ) throw new IllegalArgumentException("Listener methods must have exactly one parameter.");
        if ( !ClassReflection.isAssignableFrom(Event.class, ReflectionHelper.getFirstParameterType(method))) throw new IllegalArgumentException("Invalid parameter class. Listener method parameter must extend "+ Event.class.getName()+".");
        this.object = object;
        this.method = method;
    }

    public void handle(Event event) {
        if (event == null) throw new NullPointerException("Event required.");

        try {
            method.invoke(object, event);
        } catch (Exception e) {
            throw new RuntimeException("Could not call event.", e);
        }
    }

    public Object getObject() {
        return object;
    }

    public Method getMethod() {
        return method;
    }
}

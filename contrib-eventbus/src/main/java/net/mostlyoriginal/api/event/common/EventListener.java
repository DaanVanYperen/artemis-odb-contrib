package net.mostlyoriginal.api.event.common;

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
	protected final Class parameterType;

	/**
     * @param object
     * @param method
     */
    public EventListener(Object object, Method method) {
        if (object == null) throw new NullPointerException("Object cannot be null.");
        if (method == null) throw new NullPointerException("Method cannot be null.");
        method.setAccessible(true);
        if ( method.getParameterTypes().length != 1 ) throw new IllegalArgumentException("Listener methods must have exactly one parameter.");
	    this.parameterType = ReflectionHelper.getFirstParameterType(method);
	    if ( parameterType == Event.class ) throw new IllegalArgumentException("Parameter class cannot be Event, must be subclass.");
	    if ( !ClassReflection.isAssignableFrom(Event.class, parameterType)) throw new IllegalArgumentException("Invalid parameter class. Listener method parameter must extend "+ Event.class.getName()+".");
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

	public Class getParameterType() {
		return parameterType;
	}
}

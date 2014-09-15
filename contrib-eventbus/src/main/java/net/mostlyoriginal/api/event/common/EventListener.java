package net.mostlyoriginal.api.event.common;

import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.Method;
import net.mostlyoriginal.api.util.ReflectionHelper;

/**
 * @author Daan van Yperen
 * @todo GWT provide method support.
 */
public class EventListener implements Comparable<EventListener> {

    protected final Object object;
    protected final Method method;
	protected final Class parameterType;
	protected final int priority;

	public EventListener(Object object, Method method) {
		this(object,method,0);
	}
	/**
     * @param object
     * @param method
     */
    public EventListener(Object object, Method method, int priority) {
	    this.priority = priority;
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

	/** Object that contains method. */
    public Object getObject() {
        return object;
    }

	/** Method of listener. */
    public Method getMethod() {
        return method;
    }

	/** Type of method parameter. */
	public Class getParameterType() {
		return parameterType;
	}

	/**
	 * Priority amongst listeners.
	 * Higher values are called earlier.
	 */
	public int getPriority() {
		return priority;
	}

	@Override
	public int compareTo(EventListener o) {
		// Sort by priority descending.
		return o.priority - priority;
	}
}

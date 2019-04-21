package net.mostlyoriginal.api.event.common;

import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.Method;
import net.mostlyoriginal.api.utils.ReflectionHelper;

/**
 * @author Daan van Yperen
 * @author Greg Hibberd
 * @todo GWT provide method support.
 */
public class EventListener implements Comparable<EventListener> {

    protected final Object object;
    protected final Method method;
	protected final Class[] parameterTypes;
	protected final int assignedParameter;
	protected final int priority;
	protected final boolean skipCancelledEvents;

	/**
	 * Instance event listener.
	 *
	 * Default priority 0, does not skip cancelled events.
	 *
	 * @param object Object that contains event handler method.
	 * @param method Event handler method.
	 */
	public EventListener(Object object, Method method) {
		this(object,method,0,false);
	}

	/**
	 * Instance event listener.
	 *
	 * @param object Object that contains event handler method.
	 * @param method Event handler method.
	 * @param priority Precedence over other handlers. Higher values get called first.
	 * @param skipCancelledEvents if <code>true</code>, cancelled events skip this event listener. <code>false</code>
	 */
    protected EventListener(Object object, Method method, int priority, boolean skipCancelledEvents) {
		if (object == null) throw new NullPointerException("Object cannot be null.");
		if (method == null) throw new NullPointerException("Method cannot be null.");
		method.setAccessible(true);
		int parameters = method.getParameterTypes().length;
		if (parameters < 1) throw new IllegalArgumentException("Listener methods must have at least one parameter.");
		int assigned = -1;
		parameterTypes = new Class[parameters];
		for(int i = 0; i < parameters; i++) {
			parameterTypes[i] = ReflectionHelper.getParameterType(method, i);
			if(parameterTypes[i] == Event.class) {
				throw new IllegalArgumentException("Parameter class cannot be Event, must be subclass.");
			}
			if (ClassReflection.isAssignableFrom(Event.class, parameterTypes[i])) {
				assigned = i;
			}
		}
		if (assigned == -1) throw new IllegalArgumentException("Invalid parameter class. At least one listener method parameter must extend "+ Event.class.getName()+".");
		this.priority = priority;
		this.skipCancelledEvents = skipCancelledEvents;
		this.assignedParameter = assigned;
		this.object = object;
		this.method = method;
	}

    public void handle(Object... args) {
    	Object event = args[assignedParameter];
		if (event == null) throw new NullPointerException("Event required.");

		for (int i = 0; i < args.length; i++) {
			Class obj = args[i].getClass();
			if(obj.isPrimitive() && !parameterTypes[i].isInstance(obj)) {
				throw new IllegalArgumentException("Invalid argument " + obj.getName() + ". Expected " + parameterTypes[i].getName() + ".");
			}
		}

	    if (skipCancelledEvents)
	    {
		    if ( ClassReflection.isInstance(Cancellable.class, event) && ((Cancellable)event).isCancelled() )
		    {
			    // event can be cancelled, so do not submit!
			    return;
		    }
	    }

        try {
            method.invoke(object, args);
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
		return parameterTypes[assignedParameter];
	}

	/** Type of all method parameters. */
	public Class[] getParameterTypes() {
		return parameterTypes;
	}

	/** Index of the parameter assigned for Event. */
	public int getAssignedParameter() {
		return assignedParameter;
	}

	/**
	 * Priority amongst listeners.
	 * Higher values are called earlier.
	 */
	public int getPriority() {
		return priority;
	}

	public boolean isSkipCancelledEvents() {
		return skipCancelledEvents;
	}

	@Override
	public int compareTo(EventListener o) {
		// Sort by priority descending.
		return o.priority - priority;
	}
}

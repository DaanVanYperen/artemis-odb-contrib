package net.mostlyoriginal.api.event.common;

import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.Method;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daan van Yperen
 */
public class SubscribeAnnotationFinder implements ListenerFinderStrategy {

    @Override
    /** Find all listeners in o based on @Subscribe annotation and return as EventListeners. */
    public List<EventListener> resolve(Object o) {

        ArrayList<EventListener> listeners = new ArrayList<>();

        for (Method method : ClassReflection.getDeclaredMethods(o.getClass())) {
            if ( method.hasAnnotation(Subscribe.class))
            {
                listeners.add(new EventListener(o, method));
            }
        }

        return listeners;
    }
}

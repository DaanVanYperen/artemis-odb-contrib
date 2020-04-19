package net.mostlyoriginal.api;

import java.util.Set;

import com.artemis.Component;

public class SingletonException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SingletonException(Set<Class<? extends Component>> singletonComponents) {
        super("Components annotated with @Singleton cannot be added to entities. Singleton components: " + singletonComponents);
    }

}

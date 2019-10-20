package net.mostlyoriginal.api;

import com.artemis.Component;
import com.artemis.annotations.UnstableApi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks component as a singleton. Annotated classes must extend {@link Component}.
 *
 * SingletonPlugin will inject singleton dependencies into systems, handling singleton lifecycle.
 *
 * @see net.mostlyoriginal.api.SingletonPlugin
 * @author Daan van Yperen
 */
@Retention(RetentionPolicy.RUNTIME)
@UnstableApi
@Target(ElementType.TYPE)
public @interface Singleton {
}

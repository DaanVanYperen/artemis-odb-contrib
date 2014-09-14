package net.mostlyoriginal.api;

import java.lang.annotation.*;

/**
 * Indicates invariant dependencies between components.
 *
 * For example, an animation will always need a position.
 *
 * Just a hint.
 *
 * Deprecated: The Archetype system of Artemis will probably see this refactored.
 *
 * @author Daan van Yperen
 */
@Inherited
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Deprecated
public @interface Depends {
    public Class[] value();
    public Class[] optional() default {};
}

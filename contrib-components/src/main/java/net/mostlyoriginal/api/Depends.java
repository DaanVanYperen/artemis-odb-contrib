package net.mostlyoriginal.api;

import java.lang.annotation.*;

/**
 * Indicates invariant dependencies between components.
 *
 * For example, an animation will always need a position.
 *
 * Just a hint.
 *
 * @author Daan van Yperen
 */
@Inherited
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Depends {
    public Class[] value();
    public Class[] optional() default {};
}

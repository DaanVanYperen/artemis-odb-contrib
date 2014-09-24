package net.mostlyoriginal.api.event.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denote method as an event listener.
 *
 * Event managers are required to have an event tag.
 *
 * @author Daan van Yperen
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Subscribe {

	/**
	 * Listeners with higher priority get precedence over listeners with lower priority.
	 */
	public int priority() default 0;


	/**
	 * Do not receive cancelled events?
	 */
	boolean ignoreCancelledEvents() default false;
}
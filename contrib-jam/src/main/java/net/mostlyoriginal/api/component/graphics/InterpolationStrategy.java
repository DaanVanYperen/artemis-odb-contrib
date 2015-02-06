package net.mostlyoriginal.api.component.graphics;

/**
 * @author Daan van Yperen
 */
public interface InterpolationStrategy {
    float apply(float v1, float v2, float a);
}

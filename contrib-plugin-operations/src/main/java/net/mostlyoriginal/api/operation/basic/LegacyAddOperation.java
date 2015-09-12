package net.mostlyoriginal.api.operation.basic;

import com.artemis.Component;
import com.artemis.Entity;
import net.mostlyoriginal.api.component.Schedule;
import net.mostlyoriginal.api.operation.common.BasicOperation;

/**
 * Add component to entity.
 *
 * Not compatible with pooled components.
 * Do not use this if you want to avoid GC!
 *
 * @author Daan van Yperen
 * @see Schedule
 */
@Deprecated
public class LegacyAddOperation extends BasicOperation {

    public Component component;
    public LegacyAddOperation() {}

    @Override
    public void process(Entity e) {
        e.edit().add(component);
    }

    @Override
    public void reset() {
        super.reset();
        component = null;
    }
}

package net.mostlyoriginal.api.operation;

import com.artemis.Component;
import com.artemis.Entity;

/**
 * Script Remove component from entity by class.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.script.Schedule
 */
public class RemoveOperation extends Operation {

    public Class<? extends Component> componentClass;

    public RemoveOperation() {
    }

    @Override
    public boolean act(float delta, Entity e) {
        e.edit().remove(componentClass);
        return true;
    }

    @Override
    public void reset() {
        componentClass = null;
    }
}

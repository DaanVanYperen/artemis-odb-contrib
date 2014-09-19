package net.mostlyoriginal.api.step;

import com.artemis.Component;
import com.artemis.Entity;

/**
 * Script Remove component from entity by class.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.script.Schedule
 */
public class RemoveStep extends Step {

    public Class<? extends Component> componentClass;

    public RemoveStep() {
    }

    @Override
    public boolean act(float delta, Entity e) {
        e.edit().removeComponent(componentClass);
        return true;
    }

    @Override
    public void reset() {
        componentClass = null;
    }
}

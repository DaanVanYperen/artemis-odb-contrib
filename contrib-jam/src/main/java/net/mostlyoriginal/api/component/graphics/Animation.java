package net.mostlyoriginal.api.component.graphics;

import net.mostlyoriginal.api.component.common.ExtendedComponent;

/**
 * @author Daan van Yperen
 */
public class Animation extends ExtendedComponent<Animation> {

    public String id;
    public float age;

    @Override
    protected void reset() {
        id = null;
    }

    @Override
    public void set(Animation sprite) {
        this.id = sprite.id;
    }

    public void set(String id) {
        this.id=id;
    }
}

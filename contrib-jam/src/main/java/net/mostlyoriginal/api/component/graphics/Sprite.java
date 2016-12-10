package net.mostlyoriginal.api.component.graphics;

import net.mostlyoriginal.api.component.common.ExtendedComponent;

/**
 * @author Daan van Yperen
 */
public class Sprite extends ExtendedComponent<Sprite> {

    public String id;

    @Override
    protected void reset() {
        id = null;
    }

    @Override
    public void set(Sprite sprite) {
        this.id = sprite.id;
    }

    public void set(String id) {
        this.id=id;
    }
}

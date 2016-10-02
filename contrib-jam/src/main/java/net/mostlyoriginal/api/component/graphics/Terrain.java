package net.mostlyoriginal.api.component.graphics;

import net.mostlyoriginal.api.component.common.ExtendedComponent;

/**
 * @author Daan van Yperen
 */
public class Terrain extends ExtendedComponent<Terrain> {

    public String id;

    @Override
    protected void reset() {
        id=null;
    }

    @Override
    public void set(Terrain terrain) {
        this.id=terrain.id;
    }

    public void set(String id)
    {
        this.id=id;
    }
}

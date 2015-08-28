package net.mostlyoriginal.api.system.camera;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.camera.Camera;

/**
 * Lock camera center on camera entity.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.camera.Camera
 */
@Wire
public class EntityCameraSystem extends EntityProcessingSystem {

    private ComponentMapper<Pos> pm;
    private CameraSystem cameraSystem;

    public EntityCameraSystem() {
        super(Aspect.all(Pos.class, Camera.class));
    }

    @Override
    protected void process(Entity e) {
        final Pos pos = pm.get(e);
        cameraSystem.camera.position.x = (int)(pos.xy.x);
        cameraSystem.camera.position.y = (int)(pos.xy.y);
        cameraSystem.camera.update();
    }
}

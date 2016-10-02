package net.mostlyoriginal.api.system.camera;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.IteratingSystem;
import net.mostlyoriginal.api.component.basic.Angle;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.camera.Camera;

/**
 * Lock camera center on camera entity.
 *
 * Rotation support.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.camera.Camera
 */
@Wire
public class EntityCameraSystem extends IteratingSystem {

    private ComponentMapper<Pos> pm;
    protected ComponentMapper<Angle> mAngle;
    private CameraSystem cameraSystem;

    public EntityCameraSystem() {
        super(Aspect.all(Pos.class, Camera.class));
    }

    @Override
    protected void process(int e) {
        final Pos pos = pm.get(e);

        cameraSystem.camera.position.x = (int)(pos.xy.x);
        cameraSystem.camera.position.y = (int)(pos.xy.y);

        if ( mAngle.has(e))
        {
            cameraSystem.camera.up.set(0, 1, 0).rotate(cameraSystem.camera.direction, -mAngle.get(e).rotation);
        }

        cameraSystem.camera.update();
    }
}

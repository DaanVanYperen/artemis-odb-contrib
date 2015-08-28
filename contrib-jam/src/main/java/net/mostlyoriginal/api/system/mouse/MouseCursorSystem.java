package net.mostlyoriginal.api.system.mouse;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.mouse.MouseCursor;
import net.mostlyoriginal.api.system.camera.CameraSystem;

/**
 * Place entity at mouse cursor.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.mouse.MouseCursor
 */
@Wire
public class MouseCursorSystem extends EntityProcessingSystem {

    private ComponentMapper<Pos> pm;
    private ComponentMapper<MouseCursor> am;

    private CameraSystem cameraSystem;

    public MouseCursorSystem() {
        super(Aspect.all(Pos.class, MouseCursor.class));
    }

    private Vector3 aimAtTmp = new Vector3();

    @Override
    protected void process(Entity e) {

        final Pos pos = pm.get(e);

        aimAtTmp.set(Gdx.input.getX(), Gdx.input.getY(), 0);

        final Vector3 unproject = cameraSystem.camera.unproject(aimAtTmp);

        pos.xy.x = unproject.x;
        pos.xy.y = unproject.y;
    }
}

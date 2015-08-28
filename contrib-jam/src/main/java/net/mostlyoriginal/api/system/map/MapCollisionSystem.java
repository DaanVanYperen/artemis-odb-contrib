package net.mostlyoriginal.api.system.map;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import net.mostlyoriginal.api.component.basic.Bounds;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.map.MapSolid;
import net.mostlyoriginal.api.component.physics.Physics;
import net.mostlyoriginal.api.system.camera.CameraSystem;
import net.mostlyoriginal.api.utils.MapMask;

/**
 * Constrain entities by map collision layer.
 * <p/>
 * Tiles with property 'solid' will block the entity.
 * <p/>
 * Simplistic, no tracing, will break when the entity moves fast enough to skip tiles in a frame(clamp your frame delta!)
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.physics.Physics
 * @see net.mostlyoriginal.api.component.basic.Pos
 * @see net.mostlyoriginal.api.component.basic.Bounds
 * @see net.mostlyoriginal.api.component.map.MapSolid
 */
@Wire
public class MapCollisionSystem extends EntityProcessingSystem {

    private TiledMapSystem mapSystem;
    private CameraSystem cameraSystem;

    private boolean initialized;
    private MapMask solidMask;

    private ComponentMapper<Physics> ym;
    private ComponentMapper<Pos> pm;
    private ComponentMapper<Bounds> bm;

    public MapCollisionSystem() {
        super(Aspect.all(Physics.class, Pos.class, Bounds.class, MapSolid.class));
    }

    @Override
    protected void begin() {
        if (!initialized) {
            initialized = true;
            solidMask = mapSystem.getMask(MapWallSensorSystem.MASK_SOLID_ID);
        }
    }

    @Override
    protected void end() {
    }

    @Override
    protected void process(Entity e) {
        final Physics physics = ym.get(e);
        final Pos pos = pm.get(e);
        final Bounds bounds = bm.get(e);

        //  no math required here.
        if (physics.vx != 0 || physics.vy != 0) {

            float px = pos.xy.x + physics.vx * world.delta;
            float py = pos.xy.y + physics.vy * world.delta;

            if ((physics.vx > 0 && collides(px + bounds.maxx, py + bounds.miny + (bounds.maxy - bounds.miny) * 0.5f)) ||
                    (physics.vx < 0 && collides(px + bounds.minx, py + bounds.miny + (bounds.maxy - bounds.miny) * 0.5f))) {
                physics.vx = physics.bounce > 0 ? -physics.vx * physics.bounce : 0;
                px = pos.xy.x;
            }

            if ((physics.vy > 0 && collides(px + bounds.minx + (bounds.maxx - bounds.minx) * 0.5f, py + bounds.maxy)) ||
                    (physics.vy < 0 && collides(px + bounds.minx + (bounds.maxx - bounds.minx) * 0.5f, py + bounds.miny))) {
                physics.vy = physics.bounce > 0 ? -physics.vy * physics.bounce : 0;
            }

        }

    }

    private boolean collides(final float x, final float y) {
        return solidMask.atScreen(x, y, true);
    }
}

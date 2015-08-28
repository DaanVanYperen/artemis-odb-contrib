package net.mostlyoriginal.api.system.map;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import net.mostlyoriginal.api.component.basic.Bounds;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.map.MapWallSensor;
import net.mostlyoriginal.api.component.physics.Physics;
import net.mostlyoriginal.api.utils.MapMask;

/**
 * Detects entity proximity to solid tiles.
 * <p/>
 * Entities will be able to detect if they are on the floor, horizontal or vertical walls, and what angle the closest
 * wall is at.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.map.MapWallSensor
 */
@Wire
public class MapWallSensorSystem extends EntityProcessingSystem {

    public static final String MASK_SOLID_ID = "solid";

    private TiledMapSystem mapSystem;

    private boolean initialized;
    private MapMask solidMask;

    private ComponentMapper<Physics> ym;
    private ComponentMapper<Pos> pm;
    private ComponentMapper<Bounds> bm;
    private ComponentMapper<MapWallSensor> ws;

    public MapWallSensorSystem() {
        super(Aspect.all(MapWallSensor.class, Pos.class, Bounds.class));
    }

    @Override
    protected void begin() {
        if (!initialized) {
            initialized = true;
            solidMask = mapSystem.getMask(MASK_SOLID_ID);
        }
    }

    @Override
    protected void end() {
    }

    @Override
    protected void process(Entity e) {
        final Pos pos = pm.get(e);
        final Bounds bounds = bm.get(e);

        float px = pos.xy.x;
        float py = pos.xy.y;

        final MapWallSensor mapWallSensor = ws.get(e);

        final boolean onFloor = collides(px + bounds.minx + (bounds.maxx - bounds.minx) * 0.5f, py + bounds.miny - 1) ||
                                collides(px + bounds.minx + (bounds.maxx - bounds.minx) * 0.5f, py + bounds.miny - 2);
        final boolean onCeiling = collides(px + bounds.minx + (bounds.maxx - bounds.minx) * 0.5f, py + bounds.maxy + 1);
        final boolean onEastWall = collides(px + bounds.maxx + 1, py + bounds.miny + (bounds.maxy - bounds.miny) * 0.5f);
        final boolean onWestWall = collides(px + bounds.minx - 1, py + bounds.miny + (bounds.maxy - bounds.miny) * 0.5f);

        mapWallSensor.onVerticalSurface = onEastWall || onWestWall;
        mapWallSensor.onFloor = onFloor;
        mapWallSensor.onHorizontalSurface = onCeiling || mapWallSensor.onFloor;

        mapWallSensor.wallAngle =
                onFloor ? 90 :
                        onCeiling ? -90 :
                                onEastWall ? 0 :
                                        onWestWall ? 180 : 90;
    }

    private boolean collides(final float x, final float y) {
        return solidMask.atScreen(x, y, true);
    }
}

package net.mostlyoriginal.api.system.script;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import net.mostlyoriginal.api.component.basic.Bounds;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.graphics.Anim;
import net.mostlyoriginal.api.component.script.EntitySpawner;
import net.mostlyoriginal.api.manager.AbstractEntityFactorySystem;

/**
 * Randomly spawn entity from list.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.script.EntitySpawner
 */
@Wire
public class EntitySpawnerSystem extends EntityProcessingSystem {

    public static final MapProperties EMPTY_MAP_PROPERTIES = new MapProperties();
    private ComponentMapper<EntitySpawner> sm;
    private ComponentMapper<Pos> pm;
    private ComponentMapper<Bounds> bm;
    private ComponentMapper<Anim> am;

    private AbstractEntityFactorySystem entityFactorySystem;

    public EntitySpawnerSystem() {
        super(Aspect.all(EntitySpawner.class, Pos.class, Bounds.class));
    }

    @Override
    protected void process(Entity e) {

        final EntitySpawner spawner = sm.get(e);

        if (am.has(e)) {
            String animId = spawner.enabled ? spawner.animActiveId : spawner.animInactiveId;
            if (animId != null) {
                Anim anim = am.get(e);
                anim.id = animId;
            }
        }

        if (!spawner.enabled || spawner.entityIds.length > 0) {
            return;
        }

        if (spawner.cooldown == Float.MIN_VALUE) {
            scheduleSpawn(spawner);
            spawner.cooldown /= 4;
        }

        spawner.cooldown -= world.delta;
        if (spawner.cooldown <= 0) {
            final Pos pos = pm.get(e);
            final Bounds bounds = bm.get(e);

            scheduleSpawn(spawner);

            for (int i = 0, s = MathUtils.random(spawner.minCount, spawner.maxCount); i < s; i++) {
                entityFactorySystem.createEntity(
                        spawner.entityIds[MathUtils.random(0, spawner.entityIds.length - 1)],
                        (int) (pos.xy.x + bounds.cx()),
                        (int) (pos.xy.y + bounds.cy()), EMPTY_MAP_PROPERTIES);
            }
        }
    }

    private void scheduleSpawn(EntitySpawner spawner) {
        spawner.cooldown = MathUtils.random(spawner.minCooldown, spawner.maxCooldown);
    }
}

package net.mostlyoriginal.api.network.common;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.PlayerManager;
import com.artemis.utils.ImmutableBag;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.network.component.Networked;

/**
 * Subscribe entities that are close to any of the players entities.
 * <p/>
 * Needs to be wired up by Artemis. Depends on player manager.
 *
 * @author Daan van Yperen
 * @todo would probably better to do this differently, by player observers or something. Keep things simple for now.
 */
@Wire
public class ProximitySubscribeStrategy implements SubscribeStrategy<String> {

    protected PlayerManager playerManager;

    @Override
    public boolean inScope(String player, Entity e) {

        ImmutableBag<Entity> entitiesOfPlayer = playerManager.getEntitiesOfPlayer(player);
        for (int i = 0, s = entitiesOfPlayer.size(); i < s; i++) {
        }

        return false;
    }
}


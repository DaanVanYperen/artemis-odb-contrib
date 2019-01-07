package net.mostlyoriginal.plugin.querydsl;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.World;
import com.artemis.annotations.UnstableApi;
import com.artemis.managers.TagManager;
import com.artemis.utils.IntBag;
import net.mostlyoriginal.api.utils.Preconditions;

/**
 * Ability to query a world for entities matching a certain predicate.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.plugin.QueryDslPlugin for registering.
 */
@UnstableApi
public class Query {
    private final World world;
    private final TagManager tagManager;

    public Query(World world) {
        this.world = Preconditions.checkNotNull(world);
        this.tagManager = world.getSystem(TagManager.class);
    }

    /**
     * Get entity by tag.
     *
     * @return entity identifier, or {@code -1} if no such tag.
     */
    public int withTag(String tag) {
        Preconditions.checkNotNull(tagManager, "Make sure tagmanager is registered in your world.");
        return tagManager.getEntityId(tag);
    }

    /**
     * Get all entities matching aspect.
     * @return {@code IntBag} of entities matching aspect. Returns empty bag if no entities match aspect. */
    public Builder withAspect(Aspect.Builder aspect) {
        return new Builder(world.getAspectSubscriptionManager().get(aspect).getEntities());
    }

    /**
     * Get all entities with component.
     * This is a relatively costly operation. For performance use withAspect instead.
     *
     * @return {@code IntBag} of entities matching aspect. Returns empty bag if no entities match aspect.
     */
    public IntBag withComponent(Class<? extends Component> component) {
        return world.getAspectSubscriptionManager().get(Aspect.all(component)).getEntities();
    }

    public static class Builder {
        private final IntBag source;

        public Builder(IntBag source) {
            this.source = source;
        }

        /**
         * @return Return first element.
         */
        public int first() {
            return this.source.get(0);
        }

        public IntBag all() {
            return this.source;
        }
    }
}

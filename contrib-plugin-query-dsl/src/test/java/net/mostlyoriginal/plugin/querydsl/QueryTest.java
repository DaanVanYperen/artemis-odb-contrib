package net.mostlyoriginal.plugin.querydsl;

import com.artemis.*;
import com.artemis.annotations.UnstableApi;
import com.artemis.managers.TagManager;
import net.mostlyoriginal.plugin.QueryDslPlugin;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Daan van Yperen
 */
@UnstableApi
public class QueryTest {

    // @todo perhaps move to Generic query Query<Entity> and have each query contain one bag (or iterator).

    private final Aspect.Builder TC1_ASPECT = Aspect.all(TC1.class);

    ;

    @Test
    public void Should_be_able_to_find_entity_by_tag() {
        final TagManager tagManager = new TagManager();
        final World world = new World(new WorldConfigurationBuilder()
                .dependsOn(QueryDslPlugin.class)
                .with(tagManager)
                .build());
        final int e1 = world.create();

        tagManager.register("test", e1);

        Assert.assertEquals(e1, new Query(world).withTag("test"));
    }

    @Test
    public void Should_be_able_to_find_first_entity_matching_aspect() {
        final TagManager tagManager = new TagManager();
        final World world = new World(new WorldConfigurationBuilder()
                .dependsOn(QueryDslPlugin.class)
                .with(tagManager)
                .build());
        final Entity e1 = world.createEntity();
        e1.edit().add(new TC1());
        final Entity e2 = world.createEntity();
        e2.edit().add(new TC1());
        final Entity e3 = world.createEntity();
        e3.edit().add(new TC1());

        Assert.assertEquals(e1.getId(), new Query(world).withAspect(TC1_ASPECT).first());
    }


    @Test
    public void Should_be_able_to_find_all_entities_matching_aspect() {
        final TagManager tagManager = new TagManager();
        final World world = new World(new WorldConfigurationBuilder()
                .dependsOn(QueryDslPlugin.class)
                .with(tagManager)
                .build());
        final Entity e1 = world.createEntity();
        e1.edit().add(new TC1());
        final Entity e2 = world.createEntity();
        e2.edit().add(new TC1());
        final Entity e3 = world.createEntity();
        e3.edit().add(new TC1());

        Assert.assertEquals(3, new Query(world).withAspect(TC1_ASPECT).all().size());
    }

}
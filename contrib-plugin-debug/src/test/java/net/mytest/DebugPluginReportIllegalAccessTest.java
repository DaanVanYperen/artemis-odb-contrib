package net.mytest;

import com.artemis.*;
import com.artemis.annotations.DelayedComponentRemoval;
import com.artemis.utils.Bag;
import net.mostlyoriginal.plugin.DebugEventStacktrace;
import net.mostlyoriginal.plugin.DebugLogStrategy;
import net.mostlyoriginal.plugin.DebugPlugin;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static net.mostlyoriginal.plugin.DebugEventStacktrace.Type.BAD_PRACTICE_ADDING_COMPONENTS_TO_DELETED_ENTITY;
import static net.mostlyoriginal.plugin.DebugEventStacktrace.Type.ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY;
import static org.junit.Assert.assertTrue;

/**
 * All of these Should_report_ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY
 *
 * @author Daan van Yperen
 */
public class DebugPluginReportIllegalAccessTest {

    @Mock
    private DebugLogStrategy logStrategy;

    /**
     * World deleted entity tests
     */

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        tmpId = -1;
        tmpEntity = null;
    }

    public ComponentMapper tmpCm;
    public ComponentMapper tmpCmd;
    public int tmpId;
    public Entity tmpEntity;


    @Test
    public void Ensure_bytecode_transplants_done() {
        assertTrue(DebugPlugin.isArtemisTransformedForDebugging());
    }

    @Test
    public void When_world_edit_On_deleted_entity() {
        assertErrorRightAfterDeletion((w) -> w.edit(tmpId), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_world_edit_On_entity() {
        assertNoErrorWhenNoDelete((w) -> w.edit(tmpId));
    }

    @Test
    public void When_world_edit_On_deleted_entity_after_updateentitystates() {
        assertErrorAfterUpdateEntityStates((w) -> w.edit(tmpId), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }


    @Test
    public void When_world_compositionId_On_entity() {
        assertNoErrorWhenNoDelete((w) -> w.compositionId(tmpId));
    }

    @Test
    public void When_world_compositionId_On_deleted_entity() {
        assertErrorRightAfterDeletion((w) -> w.compositionId(tmpId), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_world_compositionId_On_deleted_entity_after_updateentitystates() {
        assertErrorAfterUpdateEntityStates((w) -> w.compositionId(tmpId), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_world_deleteEntity_On_entity() {
        assertNoErrorWhenNoDelete((w) -> w.deleteEntity(tmpEntity));
    }

    @Test
    public void When_world_deleteEntity_On_deleted_entity() {
        assertErrorRightAfterDeletion((w) -> w.deleteEntity(tmpEntity), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_world_deleteEntity_On_deleted_entity_after_updateentitystates() {
        assertErrorAfterUpdateEntityStates((w) -> w.deleteEntity(tmpEntity), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }


    @Test
    public void When_world_delete_On_entity() {
        assertNoErrorWhenNoDelete((w) -> w.delete(tmpId));
    }

    @Test
    public void When_world_delete_On_deleted_entity() {
        assertErrorRightAfterDeletion((w) -> w.delete(tmpId), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_world_delete_On_deleted_entity_after_updateentitystates() {
        assertErrorAfterUpdateEntityStates((w) -> w.delete(tmpId), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }


    @Test
    public void When_world_getentity_On_entity() {
        assertNoErrorWhenNoDelete((w) -> w.getEntity(tmpId));
    }

    @Test
    public void When_world_getentity_On_deleted_entity() {
        assertErrorRightAfterDeletion((w) -> w.getEntity(tmpId), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_world_getentity_On_deleted_entity_after_updateentitystates() {
        assertErrorAfterUpdateEntityStates((w) -> w.getEntity(tmpId), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    /**
     * Compomentmapper deleted entity tests
     */

    @Test
    public void When_componentmapper_get_On_entity() {
        assertNoErrorWhenNoDelete((w) -> tmpCm.get(tmpId));
    }

    @Test
    public void When_componentmapper_get_On_deleted_entity() {
        assertErrorRightAfterDeletion((w) -> tmpCm.get(tmpId), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_componentmapper_get_On_deleted_entity_after_updateentitystates() {
        assertErrorAfterUpdateEntityStates((w) -> tmpCm.get(tmpId), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_componentmapper_has_On_entity() {
        assertNoErrorWhenNoDelete((w) -> tmpCm.has(tmpId));
    }

    @Test
    public void When_componentmapper_has_On_deleted_entity() {
        assertErrorRightAfterDeletion((w) -> tmpCm.has(tmpId), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_componentmapper_has_On_deleted_entity_after_updateentitystates() {
        assertErrorAfterUpdateEntityStates((w) -> tmpCm.has(tmpId), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }


    @Test
    public void When_componentmapper_remove_On_entity() {
        assertErrorRightAfterDeletion((w) -> tmpCm.remove(tmpId), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_componentmapper_remove_On_deleted_entity() {
        assertNoErrorWhenNoDelete((w) -> tmpCm.remove(tmpId));
    }

    @Test
    public void When_componentmapper_remove_On_deleted_entity_after_updateentitystates() {
        assertErrorAfterUpdateEntityStates((w) -> tmpCm.remove(tmpId), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }


    @Test
    public void When_componentmapper_create_On_deleted_entity() {
        assertErrorRightAfterDeletion((w) -> tmpCm.create(tmpId), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_componentmapper_create_On_entity() {
        assertNoErrorWhenNoDelete((w) -> tmpCm.create(tmpId));
    }

    @Test
    public void When_componentmapper_create_On_deleted_entity_after_updateentitystates() {
        assertErrorAfterUpdateEntityStates((w) -> tmpCm.create(tmpId), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_componentmapper_internalCreate_On_deleted_entity() {
        assertErrorRightAfterDeletion((w) -> tmpCm.internalCreate(tmpId), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_componentmapper_internalCreate_On_entity() {
        assertNoErrorWhenNoDelete((w) -> tmpCm.internalCreate(tmpId));
    }

    @Test
    public void When_componentmapper_internalCreate_On_deleted_entity_after_updateentitystates() {
        assertErrorAfterUpdateEntityStates((w) -> tmpCm.internalCreate(tmpId), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    /**
     * Compomentmapper deleted entity tests - DELAYED REMOVAL ANNOTATION.
     *
     * Delayed removal annotation means the component lingers until deletion is finalized. The debugger
     * shouldn't report accesses as an error.
     */

    @Test
    public void When_componentmapper_for_delayed_component_get_On_deleted_entity() {
        assertNoErrorRightAfterDeletion((w) -> tmpCmd.get(tmpId));
    }

    @Test
    public void When_componentmapper_for_delayed_component_get_On_deleted_entity_after_updateentitystates() {
        assertErrorAfterUpdateEntityStates((w) -> tmpCmd.get(tmpId), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_componentmapper_for_delayed_component_has_On_deleted_entity() {
        assertNoErrorRightAfterDeletion((w) -> tmpCmd.has(tmpId));
    }

    @Test
    public void When_componentmapper_for_delayed_component_has_On_deleted_entity_after_updateentitystates() {
        assertErrorAfterUpdateEntityStates((w) -> tmpCmd.has(tmpId), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }


    @Test
    public void When_componentmapper_for_delayed_component_remove_On_entity() {
        assertNoErrorRightAfterDeletion((w) -> tmpCmd.remove(tmpId));
    }

    @Test
    public void When_componentmapper_for_delayed_component_remove_On_deleted_entity_after_updateentitystates() {
        assertErrorAfterUpdateEntityStates((w) -> tmpCmd.remove(tmpId), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }


    @Test
    public void When_componentmapper_for_delayed_component_create_On_deleted_entity() {
        assertErrorRightAfterDeletion((w) -> tmpCmd.create(tmpId), BAD_PRACTICE_ADDING_COMPONENTS_TO_DELETED_ENTITY);
    }

    @Test
    public void When_componentmapper_for_delayed_component_create_On_deleted_entity_after_updateentitystates() {
        assertErrorAfterUpdateEntityStates((w) -> tmpCmd.create(tmpId), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_componentmapper_for_delayed_component_internalCreate_On_deleted_entity() {
        assertNoErrorRightAfterDeletion((w) -> tmpCmd.internalCreate(tmpId));
    }

    @Test
    public void When_componentmapper_for_delayed_component_internalCreate_On_deleted_entity_after_updateentitystates() {
        assertErrorAfterUpdateEntityStates((w) -> tmpCmd.internalCreate(tmpId), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    /** Entity obj deleted entity tests */
    /**
     * Duplicates a lot since most of these are delegated to systems, but we are testing the API, not the internals.
     */

    @Test
    public void When_entity_getCompositionid_On_entity() {
        assertNoErrorWhenNoDelete((w) -> tmpEntity.getCompositionId());
    }

    @Test
    public void When_entity_getCompositionid_On_deleted_entity() {
        assertErrorRightAfterDeletion((w) -> tmpEntity.getCompositionId(), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_entity_getCompositionid_On_deleted_entity_after_updateentitystates() {
        assertErrorAfterUpdateEntityStates((w) -> tmpEntity.getCompositionId(), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_entity_deleteFromWorld_On_entity() {
        assertNoErrorWhenNoDelete((w) -> tmpEntity.deleteFromWorld());
    }

    @Test
    public void When_entity_deleteFromWorld_On_deleted_entity() {
        assertErrorRightAfterDeletion((w) -> tmpEntity.deleteFromWorld(), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_entity_deleteFromWorld_On_deleted_entity_after_updateentitystates() {
        assertErrorAfterUpdateEntityStates((w) -> tmpEntity.deleteFromWorld(), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_entity_getComponents_On_entity() {
        assertNoErrorWhenNoDelete((w) -> tmpEntity.getComponents(new Bag<>()));
    }

    @Test
    public void When_entity_getComponents_On_deleted_entity() {
        assertErrorRightAfterDeletion((w) -> tmpEntity.getComponents(new Bag<>()), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_entity_getComponents_On_deleted_entity_after_updateentitystates() {
        assertErrorAfterUpdateEntityStates((w) -> tmpEntity.getComponents(new Bag<>()), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_entity_getComponent_classparam_On_entity() {
        assertNoErrorWhenNoDelete((w) -> tmpEntity.getComponent(TestComponent.class));
    }

    @Test
    public void When_entity_getComponent_classparam_On_deleted_entity() {
        assertErrorRightAfterDeletion((w) -> tmpEntity.getComponent(TestComponent.class), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_entity_getComponent_classparam_On_deleted_entity_after_updateentitystates() {
        assertErrorAfterUpdateEntityStates((w) -> tmpEntity.getComponent(TestComponent.class), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_entity_getComponent_componenttypeparam_On_entity() {
        assertNoErrorWhenNoDelete((w) -> tmpEntity.getComponent(w.getComponentManager().getComponentTypes().get(0)));
    }

    @Test
    public void When_entity_getComponent_componenttypeparam_On_deleted_entity() {
        assertErrorRightAfterDeletion((w) -> tmpEntity.getComponent(w.getComponentManager().getComponentTypes().get(0)), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_entity_getComponentClass_componenttypeparam_On_deleted_entity_after_updateentitystates() {
        assertErrorAfterUpdateEntityStates((w) -> tmpEntity.getComponent(w.getComponentManager().getComponentTypes().get(0)), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_entity_edit_On_entity() {
        assertNoErrorWhenNoDelete((w) -> tmpEntity.edit());
    }

    @Test
    public void When_entity_edit_On_deleted_entity() {
        assertErrorRightAfterDeletion((w) -> tmpEntity.edit(), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }

    @Test
    public void When_entity_edit_On_deleted_entity_after_updateentitystates() {
        assertErrorAfterUpdateEntityStates((w) -> tmpEntity.edit(), ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY);
    }


    public static class TestComponent extends Component {
    }

    @DelayedComponentRemoval
    public static class DelayedTestComponent extends Component {
    }

    private BaseSystem baseSystem(BiConsumer<World, Integer> logic) {
        return new BaseSystem() {
            int invocations = 0;
            ComponentMapper<TestComponent> cm;
            ComponentMapper<DelayedTestComponent> cmd;

            @Override
            protected void initialize() {
                super.initialize();
            }

            @Override
            protected void processSystem() {
                try {
                    tmpCm = cm;
                    tmpCmd = cmd;
                    logic.accept(world, invocations++);
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                    // don't care. we only care that debug plugin was called.
                }
            }
        };
    }

    /**
     * Multi step test.
     */
    private void run(BiConsumer<World, Integer> consumer, DebugEventStacktrace.Type expectedType) {
        run(expectedType,
                baseSystem(consumer)
        );
    }


    private void assertErrorRightAfterDeletion(Consumer<World> consumer, DebugEventStacktrace.Type expectedType) {
        run((w) -> {
            tmpEntity = w.createEntity();
            tmpId = tmpEntity.getId();
            w.delete(tmpId);
            consumer.accept(w);
        }, expectedType);
    }

    private void assertNoErrorRightAfterDeletion(Consumer<World> consumer) {
        run((w) -> {
            tmpEntity = w.createEntity();
            tmpId = tmpEntity.getId();
            w.delete(tmpId);
            consumer.accept(w);
        }, null);

        assertNoError();
    }

    private void assertNoErrorWhenNoDelete(Consumer<World> consumer) {
        run((w) -> {
            tmpEntity = w.createEntity();
            tmpId = tmpEntity.getId();
            consumer.accept(w);
        },null);

        assertNoError();
    }

    private void assertNoError() {
        Mockito.verify(logStrategy, Mockito.never()).log(Matchers.argThat(new ArgumentMatcher<DebugEventStacktrace>() {
            @Override
            public boolean matches(Object argument) {
                return ((DebugEventStacktrace) argument).type == ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY;
            }
        }));
    }

    private void assertErrorAfterUpdateEntityStates(Consumer<World> consumer, DebugEventStacktrace.Type expectedType) {
        run((w, step) -> {
            switch (step) {
                case 0:
                    tmpEntity = w.createEntity();
                    tmpId = tmpEntity.getId();
                    break;
                case 1:
                    w.delete(tmpId);
                    break;
                case 2:
                    consumer.accept(w);
                    break;
            }
        }, expectedType);
    }


    /**
     * Single step test.
     */
    private void run(Consumer<World> c, DebugEventStacktrace.Type expectedType) {
        run(expectedType,
                baseSystem((w, step) -> {
                    if (step == 0) c.accept(w);
                })
        );
    }

    private void run(DebugEventStacktrace.Type expectedType, BaseSystem... systems) {
        World world = new World(new WorldConfigurationBuilder().with(new DebugPlugin(logStrategy)).with(systems).build());
        for (int i = 0; i < 10; i++) {
            world.process();
        }
        if (expectedType != null ) {
            assertType(expectedType);
        }
    }

    private void assertType(final DebugEventStacktrace.Type type) {
        Mockito.verify(logStrategy, Mockito.atLeastOnce()).log(Matchers.argThat(new ArgumentMatcher<DebugEventStacktrace>() {
            @Override
            public boolean matches(Object argument) {
                return ((DebugEventStacktrace) argument).type == type;
            }
        }));
    }
}
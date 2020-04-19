package net.mostlyoriginal.plugin;

import com.artemis.BaseSystem;
import com.artemis.Component;
import com.artemis.ComponentType;
import com.artemis.annotations.DelayedComponentRemoval;
import com.artemis.annotations.UnstableApi;

import java.util.HashMap;

import static com.artemis.utils.reflect.ClassReflection.isAnnotationPresent;

/**
 * Logs entity lifecycle and can handle performance issues.
 *
 * Detects and reports illegal access to entities, and the call site that caused it!
 *
 * Don't use this system directly. Register using DebugPlugin.
 *
 * (currently disabled) Adds a {@code DebugComponent} to all entities immediately at creation time.
 *
 * @todo this only tracks entity lifecycle, not component lifecycle.
 * @author Daan van Yperen
 * @see DebugPlugin
 */
@UnstableApi
public class DebugSystem extends BaseSystem implements LifecycleListener {
    static final CharSequence CLASS_NAME = "net.mostlyoriginal.plugin.DebugSystem";

    final DebugLogStrategy logStrategy;
    final HashMap<Integer, DebugComponent> debugComponents = new HashMap<>();

    public DebugSystem() {
        logStrategy = new SystemOutDebugLogStrategy();
    }

    /**
     * @param logStrategy Event handler for debug incidents.
     */
    public DebugSystem(DebugLogStrategy logStrategy) {
        this.logStrategy = logStrategy;
    }

    @Override
    protected void processSystem() {
    }

    /**
     * Get debug component.
     * <p>
     * Useful when the world lifecycle isn't aware of the debug component yet.
     *
     * @param entityId
     * @return debug component if available or {@code null} if none.
     */
    public DebugComponent getDebugComponent(int entityId) {
        return debugComponents.containsKey(entityId) ? debugComponents.get(entityId) : null;
    }

    boolean cleanActive = false;

    public void onLifecycleEvent(Type event, int entityId, Object optionalArg) {

        // we ignore commands given by the engine during a clean.
        if (cleanActive) {
            if (event == Type.COMPONENTMANAGER_CLEAN_POST)
                cleanActive = false;
            return;
        }

        switch (event) {
            case COMPONENTMANAGER_CLEAN_PRE:
                cleanActive = true;
                break;
            case COMPONENT_GET_PRE:
            case COMPONENT_HAS_PRE:
            case COMPONENT_REMOVE_PRE:
            case COMPONENT_CREATE_PRE:
                onComponentAccessed(entityId, asDebugType(event), (ComponentType) optionalArg);
                break;
            case ENTITY_IDENTITY_PRE:
            case ENTITY_COMPONENTS_PRE:
            case ENTITY_EDIT_PRE:
            case ENTITY_ISACTIVE_CHECK_PRE:
            case ENTITY_GET_PRE:
                onEntityAccessed(entityId, asDebugType(event));
                break;
            case ENTITY_DELETE_FINALIZED:
                onEntityDeleteFinalized(entityId);
                break;
            case ENTITY_DELETE_PLANNED:
                onEntityDeletePlanned(entityId);
                break;
            case ENTITY_CREATE_POST:
                onEntityCreated(entityId);
                break;
            case COMPONENT_INTERNAL_REMOVE_PRE:
            case COMPONENT_INTERNAL_CREATE_PRE:
                // ignore.
                break;
        }
    }

    /** Log component access events. */
    private void onComponentAccessed(int entityId, DebugEventStacktrace.Type event, ComponentType componentType) {
        // Accessing could be legal if done on entity with com.artemis.annotations.DelayedComponentRemoval
        // or when delayedcomponentremoval is default.
        final DebugComponent debugComponent = debugComponents.get(entityId);
        if (debugComponent != null && !debugComponent.entityDeletionFinalized && isDelayedRemoval(componentType.getType())) {

            // creating components on deleted entities is BAD PRACTICE and probably an error.
            if ( debugComponent.isEntityDeleted() && event == DebugEventStacktrace.Type.COMPONENT_CREATE ) {
                logStrategy.log(new DebugEventStacktrace(DebugEventStacktrace.Type.BAD_PRACTICE_ADDING_COMPONENTS_TO_DELETED_ENTITY, entityId, debugComponent.name,
                        createStacktraceStub(10), debugComponent.deletionStacktrace));
            }

            // delayed removal, and before deletion finalized, so access is still legal.
            logStrategy.log(new DebugEventStacktrace(event, entityId, debugComponent.name, createStacktraceStub(8)));
        } else {
            onEntityAccessed(entityId, event);
        }
    }

    /** Check if delayed removal is active for given component type. */
    private boolean isDelayedRemoval(Class<? extends Component> type) {
        return (world.isAlwaysDelayComponentRemoval() || isAnnotationPresent(type, DelayedComponentRemoval.class));
    }

    private DebugEventStacktrace.Type asDebugType(Type event) {
        switch (event) {
            case ENTITY_DELETE_PLANNED:
                return DebugEventStacktrace.Type.ENTITY_DELETE;
            case COMPONENT_GET_PRE:
                return DebugEventStacktrace.Type.COMPONENT_GET;
            case COMPONENT_HAS_PRE:
                return DebugEventStacktrace.Type.COMPONENT_HAS;
            case COMPONENT_REMOVE_PRE:
                return DebugEventStacktrace.Type.COMPONENT_REMOVE;
            case COMPONENT_INTERNAL_REMOVE_PRE:
                return DebugEventStacktrace.Type.COMPONENT_INTERNAL_REMOVE;
            case COMPONENT_CREATE_PRE:
                return DebugEventStacktrace.Type.COMPONENT_CREATE;
            case COMPONENT_INTERNAL_CREATE_PRE:
                return DebugEventStacktrace.Type.COMPONENT_INTERNAL_CREATE;
        }
        return DebugEventStacktrace.Type.UNKNOWN;
    }

    /** Log accessing of entity. */
    public void onEntityAccessed(int entityId, DebugEventStacktrace.Type type) {
        DebugComponent debugComponent = detectIllegalAccessOf(entityId);
        logStrategy.log(new DebugEventStacktrace(type, entityId, debugComponent != null ? debugComponent.name : "???", createStacktraceStub(8)));
    }

    /** Log planned deletion of entity. */
    public void onEntityDeletePlanned(int entityId) {
        DebugComponent debugComponent = detectIllegalAccessOf(entityId);
        debugComponent.deletionStacktrace = new DebugEventStacktrace(DebugEventStacktrace.Type.ENTITY_DELETE, entityId, debugComponent.name, createStacktraceStub(999));
        logStrategy.log(debugComponent.deletionStacktrace);
    }

    /** Log actual deletion of entity. */
    public void onEntityDeleteFinalized(int entityId) {
        DebugComponent debugComponent = debugComponents.get(entityId);
        debugComponent.entityDeletionFinalized = true;
        logStrategy.log(new DebugEventStacktrace(DebugEventStacktrace.Type.ENTITY_DELETE_FINALIZED, entityId, debugComponent.name, createStacktraceStub(999)));
    }

    /** Log creation of entity. */
    public void onEntityCreated(int entityId) {

        // forget about any existing debug component entry.
        debugComponents.remove(entityId);

        DebugComponent debugComponent = null;
        try {
            // create component for user debug sake.
            debugComponent = world.getEntity(entityId).edit().create(DebugComponent.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        debugComponent.creationStacktrace = new DebugEventStacktrace(DebugEventStacktrace.Type.ENTITY_CREATE, entityId, debugComponent.name, createStacktraceStub(8));

        // the world lifecycle is delayed such that we need our own way to resolve debug components in case of create/destroy within the same system;
        debugComponents.put(entityId, debugComponent);

        logStrategy.log(debugComponent.creationStacktrace);
    }

    /** Fetches DebugComponent for entity, log error if entity shouldn't be accessed at this time. */
    private DebugComponent detectIllegalAccessOf(int entityId) {
        DebugComponent debugComponent = debugComponents.get(entityId);
        if (debugComponent != null && debugComponent.isEntityDeleted()) {
            logStrategy.log(new DebugEventStacktrace(DebugEventStacktrace.Type.ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY, entityId, debugComponent.name,
                    createStacktraceStub(10), debugComponent.deletionStacktrace));

        }
        return debugComponent;
    }

    // we only care about part of the stacktrace. stacktraces are expensive so don't overdo it.
    private StackTraceElement[] createStacktraceStub(int maxDepth) {
        return new StackTracer(maxDepth).getStackTrace();
    }

    private static class StackTracer extends Throwable {
        private static final int FRAME_SKIP_OFFSET = 2;
        private int maxDepth;

        StackTracer(int maxDepth) {
            this.maxDepth = maxDepth;
        }

        @Override
        public StackTraceElement[] getStackTrace() {
            StackTraceElement[] trace = super.getStackTrace();

            StackTraceElement[] trimmed = new StackTraceElement[trace.length - FRAME_SKIP_OFFSET];
            System.arraycopy(trace, FRAME_SKIP_OFFSET, trimmed, 0, trimmed.length);

            return trimmed;
        }
    }
}

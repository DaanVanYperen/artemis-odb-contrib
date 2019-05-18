package net.mostlyoriginal.plugin;

import com.artemis.BaseSystem;
import com.artemis.annotations.UnstableApi;
import sun.misc.SharedSecrets;

import java.util.HashMap;

/**
 * Logs entity lifecycle and can handle performance issues.
 *
 * Detects and reports illegal access to entities, and the call site that caused it!
 *
 * Don't use this system directly. Register using DebugPlugin.
 *
 * (currently disabled) Adds a {@code DebugComponent} to all entities immediately at creation time.
 *
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

        // we ignore commands given by the engine.
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
            case ENTITY_IDENTITY_PRE:
            case ENTITY_COMPONENTS_PRE:
            case ENTITY_EDIT_PRE:
            case ENTITY_ISACTIVE_CHECK_PRE:
            case ENTITY_GET_PRE:
                onEntityAccessed(entityId, asDebugType(event));
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

    public void onEntityAccessed(int entityId, DebugEventStacktrace.Type type) {
        DebugComponent debugComponent = detectIllegalAccessOf(entityId);
        logStrategy.log(new DebugEventStacktrace(type, entityId, debugComponent != null ? debugComponent.name : "???", createStacktraceStub(8)));
    }

    public void onEntityDeletePlanned(int entityId) {
        DebugComponent debugComponent = detectIllegalAccessOf(entityId);
        debugComponent.deletionStacktrace = new DebugEventStacktrace(DebugEventStacktrace.Type.ENTITY_DELETE, entityId, debugComponent.name, createStacktraceStub(999));
        logStrategy.log(debugComponent.deletionStacktrace);
    }

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
        Exception e = new Exception();
        int ignoredDepth = 2;
        int depth = Math.min(maxDepth + ignoredDepth, SharedSecrets.getJavaLangAccess().getStackTraceDepth(e));

        StackTraceElement[] result = new StackTraceElement[depth - ignoredDepth];
        for (int frame = ignoredDepth; frame < depth; frame++) {
            // expensive
            result[frame - ignoredDepth] = SharedSecrets.getJavaLangAccess().getStackTraceElement(e, frame);
        }

        return result;
    }

/*
    @Override
    public void onEntityNotFoundException(int entityId) {
        DebugComponent debugComponent = debugComponents.get(entityId);
        if (debugComponent != null && debugComponent.isEntityDeleted()) {
            logStrategy.log(new DebugEventStacktrace(DebugEventStacktrace.Type.ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY, entityId,
                    debugComponent.name, Thread.currentThread().getStackTrace(), debugComponent.deletionStacktrace));
        } else {
            logStrategy.log(new DebugEventStacktrace(DebugEventStacktrace.Type.ERROR_ATTEMPT_TO_ACCESS_INVALID_ENTITY, entityId,
                    "*INVALID*", Thread.currentThread().getStackTrace(), null));
        }
    }*/

}

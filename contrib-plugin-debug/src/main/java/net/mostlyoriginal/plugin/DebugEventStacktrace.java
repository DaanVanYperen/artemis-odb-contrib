package net.mostlyoriginal.plugin;

import com.artemis.annotations.UnstableApi;

/**
 * Data structure for a single artemis-odb debug event.
 *
 * @author Daan van Yperen
 */
@UnstableApi
public class DebugEventStacktrace {

    /** type of event. */
    public final Type type;

    /** odb entity id. ODB recycles ids over time so be careful. */
    public final int entityId;

    /** human readable name consistent during the entity lifecycle. */
    public final String entityDebugName;

    /** Stacktrace of the event. Typically a stub as deep stacktraces are too expensive to generate. */
    public final StackTraceElement[] stacktrace;

    /** stacktrace that contains origin of error, if any. */
    public final DebugEventStacktrace cause;

    public DebugEventStacktrace(Type type, int entityId, String entityDebugName, StackTraceElement[] stacktrace) {
        this(type, entityId, entityDebugName, stacktrace, null);
    }


    public DebugEventStacktrace(Type type, int entityId, String entityDebugName, StackTraceElement[] stacktrace, DebugEventStacktrace cause) {
        this.type = type;
        this.entityId = entityId;
        this.entityDebugName = entityDebugName;
        this.stacktrace = stacktrace;
        this.cause = cause;
    }

    public boolean hasCause() {
        return (cause != null);
    }

    public enum Type {

        /** Triggered just after entity is created. */
        ENTITY_CREATE,

        /** Triggered just before component is retrieved, via {@code ComponentMapper} or otherwise. */
        COMPONENT_GET,

        /** Triggered just before component is checked, via {@code ComponentMapper} or otherwise. */
        COMPONENT_HAS,

        /** Triggered just before component is removed, via {@code ComponentMapper} or otherwise. */
        COMPONENT_REMOVE,

        /** Triggered just before component is removed internally, via {@code ComponentMapper} or otherwise. */
        COMPONENT_INTERNAL_REMOVE,

        /** Triggered just before component is created, via {@code ComponentMapper} or otherwise. */
        COMPONENT_CREATE,

        /** Triggered just before component is created internally, via {@code ComponentMapper} or otherwise. */
        COMPONENT_INTERNAL_CREATE,

        /** Triggered just before entity is scheduled for deletion. */
        ENTITY_DELETE,

        /** Triggered just before the entity is actually deleted. */
        ENTITY_DELETE_FINALIZED,

        /** Should never trigger. */
        UNKNOWN,

        /** Triggered whenever the engine attempts to access an entity that has been deleted or never existed. */
        ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY(true),

        /** Triggered whenever the engine attempts to alter an entity composition after it has been deleted. */
        BAD_PRACTICE_ADDING_COMPONENTS_TO_DELETED_ENTITY(true)
        ;

        private final boolean error;

        Type(boolean error) {
            this.error = error;
        }

        Type() {
            this.error = false;
        }

        public boolean isError() {
            return error;
        }
    }
}

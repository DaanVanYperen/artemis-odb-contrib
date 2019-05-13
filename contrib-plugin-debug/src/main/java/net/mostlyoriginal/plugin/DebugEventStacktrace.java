package net.mostlyoriginal.plugin;

import com.artemis.annotations.UnstableApi;

/**
 * @author Daan van Yperen
 */
@UnstableApi
public class DebugEventStacktrace {
    public final Type type; // type of event.
    public final int entityId; // odb entity id.
    public final String entityDebugName; // human readable name consistent during the entity lifecycle.
    public final StackTraceElement[] stacktrace; // stacktrace of the event.
    public final DebugEventStacktrace cause; // stacktrace that contains origin of error, if any.ss

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
        CREATE(false), // entity was created.
        ERROR_ATTEMPT_TO_DELETE_DELETED_ENTITY(true),
        ERROR_ATTEMPT_TO_ACCESS_DELETED_ENTITY(true),
        DELETE(false); // entity delete order issued (but not finally deleted).

        private final boolean error;

        Type(boolean error) {
            this.error = error;
        }

        public boolean isError() {
            return error;
        }
    }
}

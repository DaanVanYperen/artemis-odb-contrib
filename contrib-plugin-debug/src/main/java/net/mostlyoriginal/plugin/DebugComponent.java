package net.mostlyoriginal.plugin;

import com.artemis.Component;
import com.artemis.annotations.Transient;
import com.artemis.annotations.UnstableApi;

/**
 * Tracks debug information. Handled by {@code DebugSystem} and {@code DebugPlugin}.
 *
 * @author Daan van Yperen
 */
@Transient
@UnstableApi
public class DebugComponent extends Component {

    // stacktrace of the creation callsite for this entity.
    public DebugEventStacktrace creationStacktrace;

    // stacktrace of the deletion callsite for this entity (or NULL if none).
    public DebugEventStacktrace deletionStacktrace;

    /** Generated lifecycle name. */
    // @todo decouple name generation from component.
    public String name = AnimalNameGenerator.random();

    /** tracks if deletion has been finalized by the engine. */
    public boolean entityDeletionFinalized =false;

    public boolean isEntityDeleted() {
        return deletionStacktrace != null;
    }
}

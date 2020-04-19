package net.mostlyoriginal.plugin;

import com.artemis.annotations.UnstableApi;

/**
 * Log handler for debug plugin events.
 *
 * @author Daan van Yperen
 */
@UnstableApi
public interface DebugLogStrategy {
    /**
     * Log a mutation stacktrace.
     *
     * Since this provides the whole data structure it is up to you
     * to implement package filtering.
     *
     * @param debugEventStacktrace MutationStacktrace to log.
     */
    void log(DebugEventStacktrace debugEventStacktrace);
}

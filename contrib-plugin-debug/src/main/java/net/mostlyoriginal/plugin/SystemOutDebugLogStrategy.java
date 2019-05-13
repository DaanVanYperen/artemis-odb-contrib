package net.mostlyoriginal.plugin;

import com.artemis.annotations.UnstableApi;

/**
 * Logs debug events to system out.
 *
 * @author Daan van Yperen
 */
@UnstableApi
public class SystemOutDebugLogStrategy implements DebugLogStrategy {

    private final String[] packages;
    private boolean logErrorsOnly = false;

    /**
     * @param packages One or more packages that you want in the stacktrace.
     *                 Stack lines not containing one of these packages are hidden.
     *                 Typically this is your systems package. For example: my.game.systems
     */
    public SystemOutDebugLogStrategy(String... packages) {
        this.packages = packages;
    }

    /**
     * @param value When {@code true}, only errors are logged. When {@code false}, all events are logged.
     * @return this
     */
    public SystemOutDebugLogStrategy logErrorsOnly( boolean value) {
        logErrorsOnly = value;
        return this;
    }

    private void log(String s) {
        System.out.println(s);
    }

    /**
     * Logs mutationstacktrace if it matches one of the whitelisted packages to {@code System.out}.
     *
     * @param debugEventStacktrace MutationStacktrace to log.
     */
    @Override
    public void log(DebugEventStacktrace debugEventStacktrace) {
        if ( logErrorsOnly && !debugEventStacktrace.type.isError() ) return;

        boolean matched = false;
        boolean abort = false;
        String prefix = debugEventStacktrace.entityDebugName + "(" + debugEventStacktrace.entityId + ") " + debugEventStacktrace.type;

        for (StackTraceElement stackTraceElement : debugEventStacktrace.stacktrace) {
            String callsiteSummary = stackTraceElement.toString();
            for (String includePackage : packages) {
                // explicitly exclude callsite for ourselves.
                if (callsiteSummary.contains(includePackage) && !callsiteSummary.contains(DebugSystem.CLASS_NAME)) {
                    // we found a first match! keep reporting as long as we are inside the reported package scope
                    // because we might be inside utility logic and more elements are needed to provide context.
                    if (!matched) {
                        // first line
                        if (debugEventStacktrace.hasCause()) {
                            log("*********************");
                        }

                        log(prefix + " @ " + callsiteSummary);
                    } else {
                        // beyond first line.
                        log("       .. " + callsiteSummary);
                    }
                    matched = true;
                } else if (matched) {
                    // no longer matching packages, so done.
                    abort = true;
                }
            }
            if (abort) break;
        }

        // report the cause if we have one!
        if (matched && debugEventStacktrace.hasCause()) {
            log("Caused by " + debugEventStacktrace.cause.type + " at:");
            final boolean logErrorsOnlyOriginal = logErrorsOnly;
            logErrorsOnly = false;
            log(debugEventStacktrace.cause);
            logErrorsOnly = logErrorsOnlyOriginal;
            log("*********************");
        }

        if (!matched) {
            log(prefix + " @ excluded package.");
        }
    }
}

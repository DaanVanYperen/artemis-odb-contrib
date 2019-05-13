package net.mostlyoriginal.plugin;

import com.artemis.ArtemisPlugin;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.annotations.UnstableApi;

/**
 * Logs entity lifecycle (create/delete-issued) including game call sites, and reports additional deletes.
 * <p>
 * There is a significant performance cost when the plugin is active due to stack trace generation (and logging).
 * <p>
 * Usage:
 * <pre>
 * {@code
 *    // replace net.mostlyoriginal with your game root package.
 *    WorldConfigurationBuilder.with(DebugPlugin.thatLogsErrorsIn("net.mostlyoriginal"))
 *    WorldConfigurationBuilder.with(DebugPlugin.thatLogsEverythingIn("net.mostlyoriginal"))
 *    WorldConfigurationBuilder.with(new DebugPlugin(new MyDebugLogStrategy()));
 * }
 * </pre>
 * <p>
 * You can also register {@code DebugSystem} directly into your system hierarchy if you want more control.
 * <p>
 * Example output:
 * <pre>
 * {@code
 * SunnySalamander(1) CREATE @ net.mostlyoriginal.plugin.DebugSystem.onEntityCreated(DebugSystem.java:72)
 * SunnySalamander(1) DELETE @ net.mostlyoriginal.plugin.DebugSystem.onEntityDeleteIssued(DebugSystem.java:59)
 * *********************
 * SunnySalamander(1) ERROR_ATTEMPT_TO_DELETE_DELETED_ENTITY @ net.mostlyoriginal.plugin.DebugSystem.onEntityDeleteIssued(DebugSystem.java:53)
 * Cause (Already deleted at):
 * SunnySalamander(1) DELETE @ net.mostlyoriginal.plugin.DebugSystem.onEntityDeleteIssued(DebugSystem.java:59)
 * *********************
 * }
 * </pre>
 *
 * @author Daan van Yperen
 * @todo consider a more idiomatic logging strategy.
 * @see DebugSystem
 */
@UnstableApi
public class DebugPlugin implements ArtemisPlugin {

    private DebugLogStrategy logStrategy;

    /**
     * Produce configured DebugPlugin that only reports errors (interactions with deleted entities).
     * @param packages One or more packages that you want in the stacktrace.
     *                 Stack lines not containing one of these packages are hidden.
     *                 Typically this is your systems package. For example: my.game.systems
     * @return instance of DebugPlugin
     */
    public static DebugPlugin thatLogsErrorsIn(String... packages) {
        return new DebugPlugin(new SystemOutDebugLogStrategy(packages).logErrorsOnly(true));
    }

    /**
     * Produce configured DebugPlugin that only reports create/delete and errors.
     * @param packages One or more packages that you want in the stacktrace.
     *                 Stack lines not containing one of these packages are hidden.
     *                 Typically this is your systems package. For example: my.game.systems
     * @return instance of DebugPlugin
     */
    public static DebugPlugin thatLogsEverythingIn(String... packages) {
        return new DebugPlugin(new SystemOutDebugLogStrategy(packages));
    }

    /**
     * @param packages One or more packages that you want in the stacktrace.
     *                 Stack lines not containing one of these packages are hidden.
     *                 Typically this is your systems package. For example: my.game.systems
     */
    public DebugPlugin(String... packages) {
        this(new SystemOutDebugLogStrategy(packages));
    }

    /**
     * Create debug plugin with a custom event handler for debug incidents.
     *
     * @param logStrategy Event handler for debug incidents.
     */
    public DebugPlugin(DebugLogStrategy logStrategy) {
        this.logStrategy = logStrategy;
    }

    /**
     * Don't call this.
     */
    public DebugPlugin() {
        // here to give some feedback when users use the typical pattern of worldConfiguration.dependsOn by accident.
        throw new RuntimeException("Please see javadoc for DebugPlugin for correct use of the plugin.");
    }

    @Override
    public void setup(WorldConfigurationBuilder b) {
        b.with(WorldConfigurationBuilder.Priority.LOWEST, new DebugSystem(logStrategy));
    }

}

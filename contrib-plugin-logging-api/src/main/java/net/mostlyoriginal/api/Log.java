package net.mostlyoriginal.api;

/**
 * Logging API.
 * <p>
 * Add this class to your system, the DI will initialize it with registered implementations.
 *
 * @author Daan van Yperen
 * @since 2.3.0
 */
public interface Log {
    /**
     * Log an informational message.
     * Check {@see #isInfoEnabled} before calling this method to save some cycles.
     *
     * @param message message to log.
     */
    void info(String message);

    /**
     * Log an informational formatted string.
     * Check {@see #isInfoEnabled} before calling this method to save some cycles.
     *
     * @param message message to log as described in Format string syntax
     * @param args    arguments
     * @throws java.util.IllegalFormatException If a format string contains an illegal syntax, a format specifier that is incompatible with the given arguments, insufficient arguments given the format string, or other illegal conditions. For specification of all possible formatting errors, see the Details section of the formatter class specification.
     */
    void info(String message, Object... args);

    /**
     * Log an error.
     * Check {@see #isErrorEnabled} before calling this method to save some cycles.
     *
     * @param message message to log.
     */
    void error(String message);

    /**
     * Log an error as a formatted string.
     * Check {@see #isErrorEnabled} before calling this method to save some cycles.
     *
     * @param message message to log as described in Format string syntax
     * @param args    arguments
     * @throws java.util.IllegalFormatException If a format string contains an illegal syntax, a format specifier that is incompatible with the given arguments, insufficient arguments given the format string, or other illegal conditions. For specification of all possible formatting errors, see the Details section of the formatter class specification.
     */
    void error(String message, Object... args);

    /**
     * Log a debug message.
     * Check {@see #isDebugEnabled} before calling this method to save some cycles.
     *
     * @param message message to log.
     */
    void debug(String message);

    /**
     * Log a debug message as a formatted string.
     * Check {@see #isDebugEnabled} before calling this method to save some cycles.
     *
     * @param message message to log as described in Format string syntax
     * @param args    arguments
     * @throws java.util.IllegalFormatException If a format string contains an illegal syntax, a format specifier that is incompatible with the given arguments, insufficient arguments given the format string, or other illegal conditions. For specification of all possible formatting errors, see the Details section of the formatter class specification.
     */
    void debug(String message, Object... args);

    /**
     * @return {@code TRUE} when info logging is enabled.
     */
    boolean isInfoEnabled();

    /**
     * @return {@code TRUE} when error logging is enabled.
     */
    boolean isErrorEnabled();

    /**
     * @return {@code TRUE} when error logging is enabled.
     */
    boolean isDebugEnabled();
}

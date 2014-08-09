package net.mostlyoriginal.api.network.marshal.common;

/**
 * Connection state of marshal.
 *
 * @author Daan van Yperen
 */
public enum MarshalState {

    /** connecting. */
    STARTING,

    /** start succeeded. */
    STARTED,

    /** stopping the connection */
    STOPPING,

    /** disconnected. */
    STOPPED,

    /** failed to establish a connection / listing state. */
    FAILED_TO_START
}

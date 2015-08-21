package net.mostlyoriginal.api.network.marshal.kryonet;

import com.esotericsoftware.kryonet.Client;
import net.mostlyoriginal.api.network.marshal.common.MarshalState;

import java.io.IOException;

/**
 * Wrapper for Kryonet client connections.
 *
 * @author Daan van Yperen
 */
public class KryonetClientMarshalStrategy extends KryonetMarshalStrategy {

    protected static final int CONNECTION_TIMEOUT = 500;
    private final String host;
    private final int port;

    /**
     * Create client connection handler.
     *
     * @param host bind ip
     * @param port bind port (tcp and udp)
     */
    public KryonetClientMarshalStrategy(String host, int port) {
        this.host = host;
        this.port = port;

        endpoint = new Client();
    }

    @Override
    protected void connectEndpoint() {
        try {
            ((Client)endpoint).connect(CONNECTION_TIMEOUT, host, port,port+1);
            state = MarshalState.STARTED;
        } catch (IOException e) {
            state = MarshalState.FAILED_TO_START;
        }

    }

    @Override
    public MarshalState getState() {
        return state;
    }

    /**
     * Send object to everyone.
     * Since the client is only connected to the server, this only targets the server.
     * @todo might want to consider if we want to send from a different perspective. (client > client)
     */
    @Override
    public void sendToAll(Object o) {
        ((Client)endpoint).sendTCP(o);
    }
}

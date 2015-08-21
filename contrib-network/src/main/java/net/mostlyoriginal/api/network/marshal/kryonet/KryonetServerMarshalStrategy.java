package net.mostlyoriginal.api.network.marshal.kryonet;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import net.mostlyoriginal.api.network.marshal.common.MarshalState;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Wrapper for Kryonet client connections.
 *
 * @author Daan van Yperen
 */
public class KryonetServerMarshalStrategy extends KryonetMarshalStrategy {

    private final String host;
    private final int port;

    /**
     * Create server marshal handler.
     *
     * @param host bind ip
     * @param port bind port (tcp and udp)
     */
    public KryonetServerMarshalStrategy(String host, int port) {
        this.host = host;
        this.port = port;

        endpoint = new Server() {
            @Override
            protected Connection newConnection() {
                return super.newConnection();
            }
        };
    }

    @Override
    protected void connectEndpoint() {
        try {
            ((Server)endpoint).bind(new InetSocketAddress(host, port), new InetSocketAddress(host, port+1));
            state = MarshalState.STARTED;
        } catch (IOException e) {
            e.printStackTrace();
            state = MarshalState.FAILED_TO_START;
        }
    }

    @Override
    public void sendToAll(Object o) {
        ((Server)endpoint).sendToAllTCP(o);
    }
}

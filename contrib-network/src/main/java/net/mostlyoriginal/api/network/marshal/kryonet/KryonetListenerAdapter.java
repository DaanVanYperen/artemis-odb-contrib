package net.mostlyoriginal.api.network.marshal.kryonet;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import net.mostlyoriginal.api.network.marshal.common.MarshalObserver;

/**
 * Kryonet listener > marshal observer adapter.
 *
 * Passes on events from kryonet to a marshal observer.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.network.system.MarshalSystem
 * @see net.mostlyoriginal.api.network.marshal.common.MarshalObserver
 */
public class KryonetListenerAdapter extends Listener {
    private final MarshalObserver listener;

    public KryonetListenerAdapter(MarshalObserver listener) {
        this.listener = listener;
    }

    @Override
    public void connected(Connection connection) {
        Log.info("KryonetListenerAdapter: connected");
        listener.connected(connection.getID());
    }

    @Override
    public void disconnected(Connection connection) {
        listener.disconnected(connection.getID());
    }

    @Override
    public void received(Connection connection, Object object) {
        listener.received(connection.getID(), object);
    }
}

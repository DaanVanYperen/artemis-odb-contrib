package net.mostlyoriginal.api.network.system;

import com.artemis.systems.VoidEntitySystem;
import com.esotericsoftware.minlog.Log;
import net.mostlyoriginal.api.network.marshal.common.MarshalDictionary;
import net.mostlyoriginal.api.network.marshal.common.MarshalObserver;
import net.mostlyoriginal.api.network.marshal.common.MarshalState;
import net.mostlyoriginal.api.network.marshal.common.MarshalStrategy;

/**
 * General system for marshalling objects between worlds.
 *
 * @author Daan van Yperen
 */
public class MarshalSystem extends VoidEntitySystem implements MarshalObserver {

    private final MarshalStrategy marshal;
    private int establishedConnections;

    /**
     * Instance a new marshal system.
     *
     * @param dictionary Dictionary of all networked classes. ID's must match on client and server.
     * @param marshal Strategy used to marshal objects. (typically a variant of KryonetMarshalStrategy).
     */
    public MarshalSystem(MarshalDictionary dictionary, MarshalStrategy marshal) {
        if ( dictionary == null ) throw new IllegalArgumentException("Dictionary cannot be null.");
        if ( marshal == null ) throw new IllegalArgumentException("Strategy cannot be null.");

        this.marshal = marshal;

        marshal.setListener(this);
        marshal.setDictionary(dictionary);
    }

    /** Attempt to start marshaling. */
    public void start() {
        marshal.start();
    }

    /** Attempt to stop marshaling. */
    public void stop()
    {
        marshal.stop();
    }

    @Override
    protected void processSystem() {
            marshal.update();
    }

    @Override
    public void received(int connectionId, Object object) {
    }

    @Override
    public void disconnected(int connectionId) {
        establishedConnections--;
        Log.info("1 connection disconnected from " + marshal.getClass().getSimpleName());
    }

    @Override
    public void connected(int connectionId) {
        establishedConnections++;
        Log.info("1 connection connected from " + marshal.getClass().getSimpleName());
    }

    /** Number of currently established connections */
    public int getEstablishedConnections()
    {
        return establishedConnections;
    }

    public MarshalState getState()
    {
        return marshal.getState();
    }

    public MarshalStrategy getMarshal() {
        return marshal;
    }
}

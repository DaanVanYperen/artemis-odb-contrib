package net.mostlyoriginal.api.network.marshal.kryonet;

import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import net.mostlyoriginal.api.network.marshal.common.MarshalDictionary;
import net.mostlyoriginal.api.network.marshal.common.MarshalObserver;
import net.mostlyoriginal.api.network.marshal.common.MarshalState;
import net.mostlyoriginal.api.network.marshal.common.MarshalStrategy;

import java.util.Map;

/**
 * Abstract Kryonet marshalling strategy.
 *
 * Common functionality for
 *
 * @author Daan van Yperen
 */
public abstract class KryonetMarshalStrategy implements MarshalStrategy {

    protected Listener listener;
    protected EndPoint endpoint;
    protected MarshalDictionary dictionary;
    protected MarshalState state = MarshalState.STOPPED;

    @Override
    public void setListener(MarshalObserver listener) {
        this.listener = new KryonetListenerAdapter(listener);
    }

    @Override
    public void setDictionary(MarshalDictionary dictionary) {
        if ( dictionary == null ) throw new IllegalArgumentException("Dictionary cannot be null.");
        this.dictionary = dictionary;
    }

    /** Register all classes from dictionary with kryonet. */
    protected void registerDictionary( ) {
        endpoint.getKryo().reset();
        for (Map.Entry<Integer, Class> entry : dictionary.getItems().entrySet()) {
            endpoint.getKryo().register(entry.getValue(), entry.getKey());
        }
    }

    @Override
    public void update() {
        // handled by kryo threads.
    }

    /** Disconnect our endpoint. */
    public void stop() {
        state = MarshalState.STOPPING;
        endpoint.close();
        state = MarshalState.STOPPED;
    }

    /** Establish connection / prepare to listen. */
    @Override
    public void start() {
        state = MarshalState.STARTING;
        registerDictionary();
        endpoint.addListener(listener); // can be safely called more than once.
        Log.info("add listener to " + this.getClass().getSimpleName() + " " +  listener);
        endpoint.start();
        connectEndpoint();
    }

    protected abstract void connectEndpoint();

    @Override
    public MarshalState getState() {
        return state;
    }
}


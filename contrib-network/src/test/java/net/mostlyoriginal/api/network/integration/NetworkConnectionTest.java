package net.mostlyoriginal.api.network.integration;

import com.artemis.World;
import net.mostlyoriginal.api.network.common.NetworkIntegrationTest;
import net.mostlyoriginal.api.network.marshal.common.MarshalDictionary;
import net.mostlyoriginal.api.network.marshal.common.MarshalState;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Network integration test for kryonet connection lifecycle.
 *
 * Mockup? Smockup! @_@
 */
public class NetworkConnectionTest extends NetworkIntegrationTest {

    @Test
    public void Connect_BareClientServer_ConnectionSuccess(){

        final MarshalDictionary marshalDictionary = new MarshalDictionary();

        final World s = initializeDefaultServer(marshalDictionary);
        final World c = initializeDefaultClient(marshalDictionary);

        tickWorld(s, c, 50);

        assertEquals("Client not reporting connection.", 1, clientNetworkSystem.getEstablishedConnections());
        assertEquals("Server not reporting connection.", 1, serverNetworkSystem.getEstablishedConnections());
    }

    @Test
    public void Connect_ServerNotListening_ReportViaState(){
        final MarshalDictionary marshalDictionary = new MarshalDictionary();

        final World c = initializeDefaultClient(marshalDictionary);

        tickWorld(null, c, 50);

        assertEquals("No connection expected.", 0, clientNetworkSystem.getEstablishedConnections());
        assertEquals("Expected failed.", MarshalState.FAILED_TO_START, clientNetworkSystem.getState());
    }

    @Test
    public void Disconnect_EstablishedConnection_Disconnect(){

        final MarshalDictionary marshalDictionary = new MarshalDictionary();

        final World s = initializeDefaultServer(marshalDictionary);
        final World c = initializeDefaultClient(marshalDictionary);

        tickWorld(s, c, 50);

        clientNetworkSystem.stop();

        tickWorld(s, c, 50);

        assertEquals("Expected client reports stopped state.", MarshalState.STOPPED, clientNetworkSystem.getState());
        assertEquals("Expected client disconnected.", 0, clientNetworkSystem.getEstablishedConnections());
        assertEquals("Expected server disconnected.", 0, serverNetworkSystem.getEstablishedConnections());
    }

    @Test
    public void Disconnect_NoConnection_NoEffect(){

        final MarshalDictionary marshalDictionary = new MarshalDictionary();

        final World c = initializeDefaultClient(marshalDictionary, false);

        tickWorld(null, c, 50);
        clientNetworkSystem.stop();
        tickWorld(null, c, 50);

        assertEquals("Expected client reports stopped state.", MarshalState.STOPPED, clientNetworkSystem.getState());
    }
}
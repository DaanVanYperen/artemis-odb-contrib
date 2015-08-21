package net.mostlyoriginal.api.network.integration;

import com.artemis.Component;
import net.mostlyoriginal.api.network.common.MarshalIntegrationTest;
import net.mostlyoriginal.api.network.marshal.common.MarshalDictionary;
import net.mostlyoriginal.api.network.marshal.common.MarshalObserver;
import net.mostlyoriginal.api.network.marshal.common.MarshalStrategy;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Network integration tests for object dispatching.
 *
 * @todo better to make this a kryonet strategy dispatch test.
 * @author Daan van Yperen
 */
@Ignore
public class MarshalDispatchTest extends MarshalIntegrationTest {

    public MarshalObserver clientObserver;
    private MarshalObserver client2Observer;
    public MarshalObserver serverObserver;
    public MarshalStrategy client;
    private MarshalStrategy client2;
    public MarshalStrategy server;


    @Before
    public void setUp() throws Exception {

        // a typical client/server setup.

        clientObserver = mock(MarshalObserver.class);
        client2Observer = mock(MarshalObserver.class);
        serverObserver = mock(MarshalObserver.class);

        client = getClientStrategy();
        client2 = getClientStrategy();
        server = getServerStrategy();

        client.setListener(clientObserver);
        client2.setListener(client2Observer);
        server.setListener(serverObserver);
    }

    @Test
    public void Marshal_ClientToServerObject_Success(){

        setDictionary(new MarshalDictionary(TestComponent.class));

        // tick our world forward, and add a send request.
        server.start();
        client.start();
        tick(server, client, 100);
        client.sendToAll(new TestComponent(999));
        tick(server, client, 100);

        // did we receive the object at the other end?
        verify(serverObserver).received(any(Integer.class), refEq(new TestComponent(999)));
    }

    @Test
    public void Marshal_ServerToAllClients_ReceivedAtAllClients(){
      fail("Not yet implemented");
    }

    @Test
    public void Marshal_ServerToSpecificClient_ReceivedAtSpecificClient(){
      fail("Not yet implemented");
    }

    @Test
    public void Marshal_ClientToServerObjectNotInClientDictionary_ClientException(){
      fail("Not yet implemented");
    }

    @Test
    public void Marshal_ClientToServerObjectNotInClientDictionary_ServerException(){
      fail("Not yet implemented");
    }

    @Test
    public void Marshal_MarshalEventToServer_DispatchedOnServer(){
      fail("Not yet implemented");
    }

    @Test
    public void Marshal_MarshalThirdPartyObjectToServer_Success(){
      fail("Not yet implemented");
    }



    public static class TestComponent extends Component {
        int var;
        public TestComponent() {}
        public TestComponent(int i) {
            var=i;
        }
    }

    private void setDictionary(MarshalDictionary d) {
        client.setDictionary(d);
        server.setDictionary(d);
    }
}

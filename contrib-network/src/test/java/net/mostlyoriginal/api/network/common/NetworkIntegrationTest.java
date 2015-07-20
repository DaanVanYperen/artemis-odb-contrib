package net.mostlyoriginal.api.network.common;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import net.mostlyoriginal.api.network.marshal.common.MarshalDictionary;
import net.mostlyoriginal.api.network.marshal.kryonet.KryonetClientMarshalStrategy;
import net.mostlyoriginal.api.network.marshal.kryonet.KryonetServerMarshalStrategy;
import net.mostlyoriginal.api.network.system.MarshalSystem;
import org.junit.After;
import org.junit.Before;

/**
 * Helper for writing kryonet network integration tests.
 *
 * @author Daan van Yperen
 */
public abstract class NetworkIntegrationTest {
    public static final String TEST_HOST = "127.0.0.1";
    public static final int TEST_PORT = 27015;
    public MarshalSystem serverNetworkSystem;
    public MarshalSystem clientNetworkSystem;

    @Before
    public void setUp() throws Exception {
        serverNetworkSystem = clientNetworkSystem = null;
    }

    @After
    public void tearDown() throws Exception {
        if ( serverNetworkSystem != null ) serverNetworkSystem.stop();
        if ( clientNetworkSystem != null ) clientNetworkSystem.stop();

        // wait for things to cool down.
        Thread.sleep(50);
    }

    protected World initializeDefaultServer(MarshalDictionary marshalDictionary) {
        return initializeDefaultServer(marshalDictionary,true);
    }

    protected World initializeDefaultServer(MarshalDictionary marshalDictionary, boolean start) {

        final WorldConfiguration config = new WorldConfiguration();
        serverNetworkSystem = new MarshalSystem(marshalDictionary, new KryonetServerMarshalStrategy(TEST_HOST, TEST_PORT));
        config.setSystem(serverNetworkSystem);

        final World result = new World(config);
        if ( start ) serverNetworkSystem.start();
        return result;
    }

    protected World initializeDefaultClient(MarshalDictionary marshalDictionary) {
        return initializeDefaultClient(marshalDictionary,true);
    }

    protected World initializeDefaultClient(MarshalDictionary marshalDictionary, boolean start) {

        final WorldConfiguration config = new WorldConfiguration();
        clientNetworkSystem = new MarshalSystem(marshalDictionary, new KryonetClientMarshalStrategy(TEST_HOST, TEST_PORT));
        config.setSystem(clientNetworkSystem);

        World c = new World(config);
        if ( start ) clientNetworkSystem.start();

        return c;
    }

        // @todo server/client cleanup.
    /** Tick world for given ms, waiting frameWaitMS per frame. */
    protected void tickWorld(World server, World client, int ms) {
        long startTime = System.currentTimeMillis();

        while ( startTime + ms > System.currentTimeMillis() ) {
            if ( server != null ) server.process();
            try {
                Thread.sleep(5);
            } catch (InterruptedException ignored) {
            }
            if ( client != null ) client.process();
            try {
                Thread.sleep(5);
            } catch (InterruptedException ignored) {
            }
        }
    }

}

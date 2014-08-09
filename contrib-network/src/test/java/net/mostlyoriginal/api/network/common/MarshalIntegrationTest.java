package net.mostlyoriginal.api.network.common;

import net.mostlyoriginal.api.network.marshal.common.MarshalStrategy;
import net.mostlyoriginal.api.network.marshal.kryonet.KryonetClientMarshalStrategy;
import net.mostlyoriginal.api.network.marshal.kryonet.KryonetServerMarshalStrategy;

/**
 * Helper for writing kryonet network integration tests.
 *
 * @author Daan van Yperen
 */
public abstract class MarshalIntegrationTest {
    public static final String TEST_HOST = "127.0.0.1";
    public static final int TEST_PORT = 27015;

    public MarshalStrategy getClientStrategy() { return new KryonetClientMarshalStrategy(TEST_HOST, TEST_PORT); }
    public MarshalStrategy getServerStrategy() { return new KryonetServerMarshalStrategy(TEST_HOST, TEST_PORT); }

        // @todo server/client cleanup.
    /** Tick world for given ms, waiting frameWaitMS per frame. */
    protected void tick(MarshalStrategy server, MarshalStrategy client, int ms) {
        long startTime = System.currentTimeMillis();

        while ( startTime + ms > System.currentTimeMillis() ) {
            if ( server != null ) server.update();
            try {
                Thread.sleep(5);
            } catch (InterruptedException ignored) {
            }
            if ( client != null ) client.update();
            try {
                Thread.sleep(5);
            } catch (InterruptedException ignored) {
            }
        }
    }

}

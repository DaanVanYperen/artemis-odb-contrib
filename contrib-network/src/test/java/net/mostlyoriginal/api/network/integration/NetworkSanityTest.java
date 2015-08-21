package net.mostlyoriginal.api.network.integration;

import net.mostlyoriginal.api.network.common.NetworkIntegrationTest;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Network integration sanity tests. (matching app version + dictionary).
 *
 * @author Daan van Yperen
 */
@Ignore
public class NetworkSanityTest extends NetworkIntegrationTest {

    @Test
    public void Connect_MismatchingDictionaries_ThrowException(){
      fail("Not yet implemented");
    }

    @Test
    public void Connect_ClientServerVersionMismatch_ThrowException(){
      fail("Not yet implemented");
    }
}

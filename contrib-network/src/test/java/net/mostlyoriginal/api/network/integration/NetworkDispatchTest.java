package net.mostlyoriginal.api.network.integration;

import net.mostlyoriginal.api.network.common.NetworkIntegrationTest;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Network integration tests for object dispatching.
 *
 * @author Daan van Yperen
 */
public class NetworkDispatchTest extends NetworkIntegrationTest {

    @Test
    public void Marshal_MarshalSimpleComponentToServer_Success(){
      fail("Not yet implemented");
      // @todo tests for S>C component reintegration into the client world. ReintegrationUpdateStrategy?
      // @todo tests for C>S component validation. ReceptionStrategy?
    }

    @Test
    public void Marshal_MarshalEventToServer_DispatchedOnServer(){
      fail("Not yet implemented");
    }

    @Test
    public void Marshal_MarshalThirdPartyObjectToServer_Success(){
      fail("Not yet implemented");
    }


}

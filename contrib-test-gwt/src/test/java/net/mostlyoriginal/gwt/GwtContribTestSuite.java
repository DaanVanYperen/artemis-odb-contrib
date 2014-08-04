package net.mostlyoriginal.gwt;

import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * Runs contribution tests.
 *
 * @author Daan van Yperen
 */
public class GwtContribTestSuite extends TestCase {

    public static Test suite() {
      GWTTestSuite suite = new GWTTestSuite("Tests for artemis-odb-contrib.");
      suite.addTestSuite(BasicArtemisTest.class);
      return suite;
    }
}

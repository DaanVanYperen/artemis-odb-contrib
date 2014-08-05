package net.mostlyoriginal;

import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestCase;
import net.mostlyoriginal.gwt.BasicArtemisGwtTest;
import net.mostlyoriginal.gwt.InterSystemEventGwtTest;
import net.mostlyoriginal.gwt.SubscribeAnnotationFinderGwtTest;

/**
 * Runs contribution tests.
 *
 * Adding new tests:
 *
 * 1. Name all classes 'GwtTest' or they might run under Java.
 * 2. Add gwt test classes to 'tests' array.
 *
 * Surefire is able to run these tests as well, so to avoid mistakes
 * the BasicArtemisGwtTest.class has a test to check for the javascript
 * environment.
 *
 * @author Daan van Yperen
 */
public class GwtContribTestSuite extends TestCase {

    @SuppressWarnings("unchecked")
    private static Class<? extends TestCase>[] tests = new Class[]{

            BasicArtemisGwtTest.class,
            SubscribeAnnotationFinderGwtTest.class,
            InterSystemEventGwtTest.class
    };

    public static Test suite() {
      GWTTestSuite suite = new GWTTestSuite("Tests for artemis-odb-contrib.");
        for (Class<? extends TestCase> aCase : tests) {
            suite.addTestSuite(aCase);
        }
        suite.addTestSuite(BasicArtemisGwtTest.class);
      return suite;
    }
}

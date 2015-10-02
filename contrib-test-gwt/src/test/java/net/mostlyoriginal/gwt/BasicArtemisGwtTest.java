package net.mostlyoriginal.gwt;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.managers.TagManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import net.mostlyoriginal.gwt.system.CountingTestSystem;

/**
 * Basic sanity checks. Are these tests running under gwt?
 *
 * @author Daan van Yperen
 */
public class BasicArtemisGwtTest extends GWTTestCase {

    @Override
    public String getModuleName() {
        return "net.mostlyoriginal.ContribTest";
    }

    /** Make sure we are running on client, and not java vm. D: */
    public void test_paranoid_running_under_gwt() {
        assertTrue(GWT.isScript());
    }

    public void test_runartemis_processsimplesystem_noexceptions() throws Exception {
        WorldConfiguration config = new WorldConfiguration();
        config.setSystem(new TagManager());

        CountingTestSystem s = new CountingTestSystem();
        config.setSystem(s);

        World w = new World(config);
        w.process();

        assertEquals(1, s.count);
    }
}

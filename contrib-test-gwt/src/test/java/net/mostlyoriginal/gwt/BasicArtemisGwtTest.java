package net.mostlyoriginal.gwt;

import com.artemis.World;
import com.artemis.managers.TagManager;
import com.google.gwt.junit.client.GWTTestCase;
import net.mostlyoriginal.gwt.system.CountingTestSystem;

/**
 * @author Daan van Yperen
 */
public class BasicArtemisGwtTest extends GWTTestCase {

    @Override
    public String getModuleName() {
        return "net.mostlyoriginal.ContribTest";
    }

    public void test_runartemis_processsimplesystem_noexceptions() throws Exception {
        World w = new World();
        w.setManager(new TagManager());

        CountingTestSystem s = new CountingTestSystem();
        w.setSystem(s);
        w.initialize();
        w.process();

        assertEquals(1, s.count);
    }
}

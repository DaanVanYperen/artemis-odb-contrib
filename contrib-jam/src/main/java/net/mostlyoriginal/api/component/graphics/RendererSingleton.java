package net.mostlyoriginal.api.component.graphics;

import com.artemis.Component;
import com.artemis.annotations.Transient;
import net.mostlyoriginal.api.Singleton;

/**
 * @author Daan van Yperen
 */
@Singleton
@Transient
public class RendererSingleton extends Component {
    public boolean sortedDirty = true;
}

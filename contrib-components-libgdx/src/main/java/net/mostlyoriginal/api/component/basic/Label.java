package net.mostlyoriginal.api.component.basic;

import com.artemis.Component;

/**
 * Label the entity.
 *
 * @author Daan van Yperen
 */
public class Label extends Component {
    public String label;

    public Label(String label) {
        this.label = label;
    }
}

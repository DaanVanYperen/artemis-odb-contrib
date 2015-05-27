package net.mostlyoriginal.api.component.ui;

import com.artemis.Component;

/**
 * @author Daan van Yperen
 */
public class Label extends Component {

    public String text;
    public Align align = Align.LEFT;

    public float scale = 1f;

    public Label(String text) {
        this.text = text;
    }

    public enum Align {
        LEFT, RIGHT
    }
}

package net.mostlyoriginal.api.component.ui;

import com.artemis.Component;

/**
 * @author Daan van Yperen
 */
public class Label extends Component {

    public String text;
    public String fontName;
    public Align align = Align.LEFT;

    public float scale = 1f;

    public Label(String text) {
        this.text = text;
    }

    public Label(String text, String fontName) {
        this.text = text;
        this.fontName = fontName;
    }


    public enum Align {
        LEFT, RIGHT
    }
}

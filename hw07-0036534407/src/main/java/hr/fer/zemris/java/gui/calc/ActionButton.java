package hr.fer.zemris.java.gui.calc;

import javax.swing.*;

public class ActionButton extends JButton {
    public ActionButton(String text, Runnable action) {
        Util.setStyle(this);
        this.setText(text);
        this.addActionListener(e -> action.run());
    }
}

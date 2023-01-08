package hr.fer.zemris.java.gui.calc;

import javax.swing.*;
import java.awt.*;

public class Util {

    public static void setStyle(JComponent component) {
        component.setBackground(new Color(0xFDF2E9));
        component.setForeground(new Color(0x1A5276));
        component.setFont(new Font("Monospace", Font.PLAIN, 20));
        component.setBorder(BorderFactory.createBevelBorder(1, new Color(0x1A5276), new Color(0xFDF2E9)));
        component.setOpaque(true);
    }
}

package hr.fer.zemris.java.gui.layouts.demo;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import javax.swing.*;
import java.awt.*;

public class Demo1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLocation(20, 20);
        frame.setSize(500, 200);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        Container p = frame.getContentPane();
        p.setLayout(new CalcLayout(3));
        p.setSize(500, 200);
        p.add(new JLabel("1,1"), new RCPosition(1,1));
        p.add(new JLabel("1,6"), new RCPosition(1,6));
        p.add(new JLabel("1,7"), new RCPosition(1,7));
        for (int i = 2; i <= 5; i++) {
            for (int j = 1; j <= 7; j++) {
                p.add(new JLabel(i + "," + j), new RCPosition(i,j));
            }
        }

//        p.add(new JLabel("x"), new RCPosition(1,1));
//        p.add(new JLabel("y"), new RCPosition(2,3));
//        p.add(new JLabel("z"), new RCPosition(2,7));
//        p.add(new JLabel("w"), new RCPosition(4,2));
//        p.add(new JLabel("a"), new RCPosition(4,5));
//        p.add(new JLabel("b"), new RCPosition(4,7));

        frame.setVisible(true);
    }
}

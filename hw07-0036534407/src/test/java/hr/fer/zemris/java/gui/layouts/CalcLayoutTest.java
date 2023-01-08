package hr.fer.zemris.java.gui.layouts;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class CalcLayoutTest {

    @Test
    public void testAddLayoutComponent1() {
        JPanel panel = new JPanel(new CalcLayout(2));

        assertThrows(CalcLayoutException.class, () -> panel.add(new JLabel("0,1"), new RCPosition(0, 1)));
        assertThrows(CalcLayoutException.class, () -> panel.add(new JLabel("1,0"), new RCPosition(1, 0)));
        assertThrows(CalcLayoutException.class, () -> panel.add(new JLabel("1,8"), new RCPosition(1, 8)));
        assertThrows(CalcLayoutException.class, () -> panel.add(new JLabel("6,1"), new RCPosition(6, 1)));
    }

    @Test
    public void testAddLayoutComponent2() {
        JPanel panel = new JPanel(new CalcLayout(2));

        assertThrows(CalcLayoutException.class, () -> {
            panel.add(new JLabel("1,2"), new RCPosition(1, 2));
        });
        assertThrows(CalcLayoutException.class, () -> {
            panel.add(new JLabel("1,3"), new RCPosition(1, 3));
        });
        assertThrows(CalcLayoutException.class, () -> {
            panel.add(new JLabel("1,4"), new RCPosition(1, 4));
        });
        assertThrows(CalcLayoutException.class, () -> {
            panel.add(new JLabel("1,5"), new RCPosition(1, 5));
        });
    }

    @Test
    public void testAddLayoutComponent3() {
        JPanel panel = new JPanel(new CalcLayout(2));

        assertThrows(CalcLayoutException.class, () -> {
            panel.add(new JLabel("2,2"), new RCPosition(2, 2));
            panel.add(new JLabel("2,2"), new RCPosition(2, 2));
        });
    }

    @Test
    public void testPreferredLayoutSize1() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
        JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
        p.add(l1, new RCPosition(2,2));
        p.add(l2, new RCPosition(3,3));
        Dimension dim = p.getPreferredSize();

        assertEquals(152, dim.width);
        assertEquals(158, dim.height);
    }

    @Test
    public void testPreferredLayoutSize2() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
        JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
        p.add(l1, new RCPosition(1,1));
        p.add(l2, new RCPosition(3,3));
        Dimension dim = p.getPreferredSize();

        assertEquals(152, dim.width);
        assertEquals(158, dim.height);
    }
}

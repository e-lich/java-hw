package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import java.awt.*;

public class PrimDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(200, 200);
        frame.setLayout(new BorderLayout());

        PrimListModel model = new PrimListModel();

        JPanel panel = new JPanel(new GridLayout(1, 2));
        frame.add(panel, BorderLayout.CENTER);

        JList<Integer> list1 = new JList<>(model.getPrimes().toArray(new Integer[0]));
        JList<Integer> list2 = new JList<>(model.getPrimes().toArray(new Integer[0]));
        panel.add(new JScrollPane(list1));
        panel.add(new JScrollPane(list2));

        JButton button = new JButton("Next");
        button.addActionListener(e -> {
            model.next();
            list1.setListData(model.getPrimes().toArray(new Integer[0]));
            list2.setListData(model.getPrimes().toArray(new Integer[0]));
        });

        frame.add(button, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}

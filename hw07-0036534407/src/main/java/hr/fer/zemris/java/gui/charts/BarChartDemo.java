package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class BarChartDemo extends JFrame {
    public BarChartDemo() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLayout(new BorderLayout());
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = Files.newBufferedReader(Path.of(args[0]));

        String xAxis = reader.readLine();
        String yAxis = reader.readLine();

        XYValue[] values = Arrays.stream(reader.readLine().split(" "))
                .map(s -> s.split(","))
                .map(s -> new XYValue(Integer.parseInt(s[0]), Integer.parseInt(s[1])))
                .toArray(XYValue[]::new);

        int yMin = Integer.parseInt(reader.readLine());
        int yMax = Integer.parseInt(reader.readLine());
        int yStep = Integer.parseInt(reader.readLine());

        reader.close();

        BarChart chart = new BarChart(xAxis, yAxis, yMin, yMax, yStep, values);

        BarChartComponent component = new BarChartComponent(chart);

        JLabel label = new JLabel(args[0]);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        BarChartDemo frame = new BarChartDemo();
        frame.add(component, BorderLayout.CENTER);
        frame.add(label, BorderLayout.NORTH);
        frame.setVisible(true);
    }
}

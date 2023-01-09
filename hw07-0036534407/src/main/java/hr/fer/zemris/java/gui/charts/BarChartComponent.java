package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class BarChartComponent extends JComponent {
    private BarChart chart;

    public BarChartComponent(BarChart chart) {
        this.chart = chart;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int xStart = 50 + g.getFontMetrics().stringWidth(String.valueOf(chart.getYMax()));

        // x axis
        int xLength = this.getWidth() - 20 - xStart - 10;
        int xSteps = chart.getValues().length;
        int xStep = xLength / xSteps;
        for (int i = 0; i <= xSteps; i++) {
            g.drawLine(xStart + i * xStep, this.getHeight() - 70, xStart + i * xStep, this.getHeight() - 65);
        }


        for (XYValue value : chart.getValues()) {
            g.drawChars(String.valueOf(value.getX()).toCharArray(), 0,
                    String.valueOf(value.getX()).length(),
                    xStart + (value.getX() - 1) * xStep + (xStep - g.getFontMetrics().stringWidth(String.valueOf(value.getX()))) / 2,
                    this.getHeight() - 50);
        }

        g.drawLine(xStart, this.getHeight() - 70, this.getWidth() - 20, this.getHeight() - 70);


        // y axis
        int yLength = this.getHeight() - 70 - 30;
        int ySteps = (chart.getYMax() - chart.getYMin()) / chart.getYStep();
        int yStep = yLength / ySteps;
        for (int i = 0; i <= ySteps; i++) {
            g.drawLine(xStart - 5, this.getHeight() - 70 - i * yStep, xStart, this.getHeight() - 70 - i * yStep);
            g.drawChars(String.valueOf(chart.getYMin() + i * chart.getYStep()).toCharArray(), 0,
                    String.valueOf(chart.getYMin() + i * chart.getYStep()).length(),
                    xStart - 10 - g.getFontMetrics().stringWidth(String.valueOf(chart.getYMin() + i * chart.getYStep())),
                    this.getHeight() - 75 - i * yStep + g.getFontMetrics().getHeight() / 2);

        }

        g.drawLine(xStart, 20, xStart, this.getHeight() - 70);

        // x axis arrow
        g.drawLine(this.getWidth() - 25, this.getHeight() - 75, this.getWidth() - 20, this.getHeight() - 70);
        g.drawLine(this.getWidth() - 25, this.getHeight() - 65, this.getWidth() - 20, this.getHeight() - 70);

        // y axis arrow
        g.drawLine(xStart - 5, 25, xStart, 20);
        g.drawLine(xStart + 5, 25, xStart, 20);

        // columns
        for (XYValue value : chart.getValues()) {
            g.setColor(new Color(0x1A5276));
            g.fillRect(xStart + (value.getX() - 1) * xStep + 1, this.getHeight() - 70 - (value.getY() - chart.getYMin()) * yStep / chart.getYStep(), xStep - 1, (value.getY() - chart.getYMin()) * yStep / chart.getYStep());
        }

        // x axis title
        g.drawChars(chart.getXAxis().toCharArray(), 0,
                chart.getXAxis().length(),
                xStart + (this.getWidth() - 20 - xStart - g.getFontMetrics().stringWidth(chart.getXAxis())) / 2,
                this.getHeight() - 20);

        // y axis title
        AffineTransform at = new AffineTransform();
        at.rotate(-Math.PI / 2);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setTransform(at);
        g2d.setFont(new Font("Arial", Font.PLAIN, 26));
        g2d.drawChars(chart.getYAxis().toCharArray(), 0,
                chart.getYAxis().length(),
                - this.getHeight() + (this.getHeight() * 2 - 300 - g2d.getFontMetrics().stringWidth(chart.getYAxis())) / 2,
                50);
        g2d.setTransform(new AffineTransform());
    }
}

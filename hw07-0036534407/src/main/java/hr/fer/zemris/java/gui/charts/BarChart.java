package hr.fer.zemris.java.gui.charts;

public class BarChart {
    private XYValue[] values;
    private String xAxis;
    private String yAxis;
    private int yMin;
    private int yMax;
    private int yStep;

    public BarChart(String xAxis, String yAxis, int yMin, int yMax, int yStep, XYValue...values) {
        if (yMin < 0 || yMax <= yMin) throw new IllegalArgumentException();

        if ((yMax - yMin) % yStep != 0) {
            yMax = yMin + ((yMax - yMin) / yStep + 1) * yStep;
        }

        for (XYValue value : values) {
            if (value.getY() < yMin) throw new IllegalArgumentException();
        }

        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.yMin = yMin;
        this.yMax = yMax;
        this.yStep = yStep;
        this.values = values;
    }

    public XYValue[] getValues() {
        return values;
    }

    public String getXAxis() {
        return xAxis;
    }

    public String getYAxis() {
        return yAxis;
    }

    public int getYMin() {
        return yMin;
    }

    public int getYMax() {
        return yMax;
    }

    public int getYStep() {
        return yStep;
    }
}

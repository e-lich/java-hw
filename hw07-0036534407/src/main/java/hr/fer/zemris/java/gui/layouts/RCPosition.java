package hr.fer.zemris.java.gui.layouts;

public class RCPosition {
    private final int row;
    private final int column;

    public RCPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public static RCPosition parse(String text) {
        String[] parts = text.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid format of RCPosition");
        }

        return new RCPosition(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    @Override
    public String toString() {
        return "RCPosition{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}

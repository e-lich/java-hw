package hr.fer.zemris.java.gui.layouts;

import java.awt.*;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CalcLayout implements LayoutManager2 {
    private static final int rows = 5;
    private static final int columns = 7;
    private final int gap;
    private final Component[][] components = new Component[rows][columns];

    private int[] calculateRowHeights(int height) {
        int heightWithoutGap = height - (rows - 1) * gap;
        int rowHeight = heightWithoutGap / rows;
        int diff = heightWithoutGap % rows;

        return switch (diff) {
            case 0 -> new int[] {rowHeight, rowHeight, rowHeight, rowHeight, rowHeight};
            case 1 -> new int[] {rowHeight, rowHeight, rowHeight + 1, rowHeight, rowHeight};
            case 2 -> new int[] {rowHeight, rowHeight + 1, rowHeight, rowHeight + 1, rowHeight};
            case 3 -> new int[] {rowHeight + 1, rowHeight, rowHeight + 1, rowHeight, rowHeight + 1};
            case 4 -> new int[] {rowHeight + 1, rowHeight + 1, rowHeight, rowHeight + 1, rowHeight + 1};
            default -> throw new IllegalStateException("Window height is too small");
        };
    }

    private int[] calculateColumnWidths(int width) {
        int widthWithoutGap = width - (columns - 1) * gap;
        int columnWidth = widthWithoutGap / columns;
        int diff = widthWithoutGap % columns;

        return switch (diff) {
            case 0 -> new int[] {columnWidth, columnWidth, columnWidth, columnWidth, columnWidth, columnWidth, columnWidth};
            case 1 -> new int[] {columnWidth, columnWidth, columnWidth, columnWidth + 1, columnWidth, columnWidth, columnWidth};
            case 2 -> new int[] {columnWidth + 1, columnWidth, columnWidth, columnWidth, columnWidth, columnWidth, columnWidth + 1};
            case 3 -> new int[] {columnWidth, columnWidth + 1, columnWidth, columnWidth + 1, columnWidth, columnWidth + 1, columnWidth};
            case 4 -> new int[] {columnWidth + 1, columnWidth, columnWidth + 1, columnWidth, columnWidth + 1, columnWidth, columnWidth + 1};
            case 5 -> new int[] {columnWidth, columnWidth + 1, columnWidth + 1, columnWidth + 1, columnWidth + 1, columnWidth + 1, columnWidth};
            case 6 -> new int[] {columnWidth + 1, columnWidth + 1, columnWidth + 1, columnWidth, columnWidth + 1, columnWidth + 1, columnWidth + 1};
            default -> throw new IllegalStateException("Window width is too small");
        };
    }

    private Dimension calculateSize(BiFunction<Integer, Integer, Integer> mathFunc, Function<Component, Dimension> dimensionFunc) {
        int initial = 0;
        if (mathFunc.apply(2, 3) == 2) {
            initial = Integer.MAX_VALUE;
        }

        int width = initial;
        int height = initial;
        Dimension dimension;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (components[i][j] != null) {
                    if ((dimension = dimensionFunc.apply(components[i][j])) != null) {
                        if (i == 0 && j == 0) {
                            width = mathFunc.apply(width, (dimension.width - 4 * gap) / 5);
                        } else {
                            width = mathFunc.apply(width, dimension.width);
                        }

                        height = mathFunc.apply(height, dimension.height);
                    }
                }
            }
        }

        return new Dimension(width * columns + gap * (columns - 1),
                height * rows + gap * (rows - 1));
    }

    public CalcLayout() {
        this(0);
    }

    public CalcLayout(int gap) {
        this.gap = gap;
    }

    /**
     * Adds the specified component to the layout, using the specified
     * constraint object.
     *
     * @param comp        the component to be added
     * @param constraints where/how the component is added to the layout.
     */
    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        if (comp == null || constraints == null) {
            throw new NullPointerException();
        }

        if (constraints instanceof String) {
            try {
                constraints = RCPosition.parse((String) constraints);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid format of RCPosition");
            }
        }

        if (!(constraints instanceof RCPosition)) {
            throw new IllegalArgumentException("Invalid constraint");
        }

        RCPosition position = (RCPosition) constraints;

        if (position.getRow() < 1 ||
                position.getRow() > rows ||
                position.getColumn() < 1 ||
                position.getColumn() > columns) {
            throw new CalcLayoutException("Invalid position");
        }

        if (position.getRow() == 1 && position.getColumn() > 1 && position.getColumn() < 6) {
            throw new CalcLayoutException("Invalid position");
        }

        if (components[position.getRow() - 1][position.getColumn() - 1] != null) {
            throw new CalcLayoutException("Position already occupied");
        }

        components[position.getRow() - 1][position.getColumn() - 1] = comp;

    }

    /**
     * Calculates the maximum size dimensions for the specified container,
     * given the components it contains.
     *
     * @param target the target container
     * @return the maximum size of the container
     * @see Component#getMaximumSize
     * @see LayoutManager
     */
    @Override
    public Dimension maximumLayoutSize(Container target) {
        return calculateSize(Math::min, Component::getMaximumSize);
    }

    /**
     * Returns the alignment along the x-axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     *
     * @param target the target container
     * @return the x-axis alignment preference
     */
    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    /**
     * Returns the alignment along the y-axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     *
     * @param target the target container
     * @return the y-axis alignment preference
     */
    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    /**
     * Invalidates the layout, indicating that if the layout manager
     * has cached information it should be discarded.
     *
     * @param target the target container
     */
    @Override
    public void invalidateLayout(Container target) {

    }

    /**
     * If the layout manager uses a per-component string,
     * adds the component {@code comp} to the layout,
     * associating it
     * with the string specified by {@code name}.
     *
     * @param name the string to be associated with the component
     * @param comp the component to be added
     */
    @Override
    public void addLayoutComponent(String name, Component comp) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the specified component from the layout.
     *
     * @param comp the component to be removed
     */
    @Override
    public void removeLayoutComponent(Component comp) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (components[i][j] == comp) {
                    components[i][j] = null;
                    return;
                }
            }
        }
    }

    /**
     * Calculates the preferred size dimensions for the specified
     * container, given the components it contains.
     *
     * @param parent the container to be laid out
     * @return the preferred dimension for the container
     * @see #minimumLayoutSize
     */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return calculateSize(Math::max, Component::getPreferredSize);
    }

    /**
     * Calculates the minimum size dimensions for the specified
     * container, given the components it contains.
     *
     * @param parent the component to be laid out
     * @return the minimum dimension for the container
     * @see #preferredLayoutSize
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return calculateSize(Math::max, Component::getMinimumSize);
    }

    /**
     * Lays out the specified container.
     *
     * @param parent the container to be laid out
     */
    @Override
    public void layoutContainer(Container parent) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Component comp = components[i][j];
                RCPosition position = new RCPosition(i + 1, j + 1);
                if (comp != null) {

                    int[] rowHeights = calculateRowHeights(parent.getHeight());
                    int[] columnWidths = calculateColumnWidths(parent.getWidth());

                    if (position.getRow() == 1 && position.getColumn() == 1) {
                        comp.setBounds(0, 0, Arrays.stream(columnWidths).limit(5).sum() + gap * 4, rowHeights[0]);
                    } else {
                        int rowOffset = Arrays.stream(rowHeights).limit(position.getRow() - 1).sum() + gap * (position.getRow() - 1);
                        int columnOffset = Arrays.stream(columnWidths).limit(position.getColumn() - 1).sum() + gap * (position.getColumn() - 1);

                        comp.setBounds(columnOffset, rowOffset, columnWidths[position.getColumn() - 1], rowHeights[position.getRow() - 1]);
                    }
                }
            }
        }

    }
}

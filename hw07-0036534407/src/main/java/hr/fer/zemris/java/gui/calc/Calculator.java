package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Stack;

public class Calculator {
    private final CalcModelImpl model;
    private final JFrame frame;
    private final Stack<Double> stack;
    private final JLabel displayLabel;
    private final JCheckBox inv;

    private void initGUI() {

        Container cp = frame.getContentPane();
        cp.setLayout(new CalcLayout(3));

        // display
        JPanel display = new JPanel(new BorderLayout());
        display.setBorder(BorderFactory.createBevelBorder(1, new Color(0x1A5276), new Color(0xFAE5D3)));
        display.setBackground(new Color(0xF6DDCC));
        display.setOpaque(true);

        displayLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        displayLabel.setBorder(new EmptyBorder(0, 0, 0, 20));
        displayLabel.setForeground(new Color(0x1A5276));
        displayLabel.setFont(new Font("Monospace", Font.BOLD, 30));

        display.add(displayLabel, BorderLayout.CENTER);

        cp.add(display, new RCPosition(1, 1));

        // calculator functions
        cp.add(new ActionButton("clr", model::clear), new RCPosition(1, 7));

        cp.add(new ActionButton("res", model::clearAll), new RCPosition(2, 7));

        cp.add(new ActionButton("push", () -> stack.push(model.getValue())), new RCPosition(3, 7));

        cp.add(new ActionButton("pop", () -> {
            if (stack.isEmpty()) {
                System.out.println("Stack is empty!");
            } else {
                model.setValue(stack.pop());
            }
        }), new RCPosition(4, 7));

        Util.setStyle(inv);
        inv.setBorderPainted(true);
        cp.add(inv, new RCPosition(5, 7));

        cp.add(new ActionButton("+/-", model::swapSign), new RCPosition(5, 4));

        cp.add(new ActionButton(".", model::insertDecimalPoint), new RCPosition(5, 5));

        // unary functions
        cp.add(new UnaryFunctionButton("1/x", "1/x", (x) -> 1 / x, (x) -> 1 / x, model, inv), new RCPosition(2, 1));
        cp.add(new UnaryFunctionButton("sin", "arcsin", Math::sin, Math::asin, model, inv), new RCPosition(2, 2));
        cp.add(new UnaryFunctionButton("log", "10^x", Math::log10, (x) -> Math.pow(10, x), model, inv), new RCPosition(3, 1));
        cp.add(new UnaryFunctionButton("cos", "arccos", Math::cos, Math::acos, model, inv), new RCPosition(3, 2));
        cp.add(new UnaryFunctionButton("ln", "e^x", Math::log, Math::exp, model, inv), new RCPosition(4, 1));
        cp.add(new UnaryFunctionButton("tan", "arctan", Math::tan, Math::atan,model, inv), new RCPosition(4, 2));
        cp.add(new UnaryFunctionButton("ctg", "arcctg", (x) -> Math.tan(1 / x), (x) -> Math.atan(1 / x), model, inv), new RCPosition(5, 2));

        // binary functions
        cp.add(new BinaryFunctionButton("=", model, inv), new RCPosition(1, 6));
        cp.add(new BinaryFunctionButton("/", model, inv), new RCPosition(2, 6));
        cp.add(new BinaryFunctionButton("*", model, inv), new RCPosition(3, 6));
        cp.add(new BinaryFunctionButton("-", model, inv), new RCPosition(4, 6));
        cp.add(new BinaryFunctionButton("+", model, inv), new RCPosition(5, 6));
        cp.add(new BinaryFunctionButton("x^n", model, inv), new RCPosition(5, 1));

        // numbers
        cp.add(new NumberButton(0, model), new RCPosition(5, 3));
        cp.add(new NumberButton(1, model), new RCPosition(4, 3));
        cp.add(new NumberButton(2, model), new RCPosition(4, 4));
        cp.add(new NumberButton(3, model), new RCPosition(4, 5));
        cp.add(new NumberButton(4, model), new RCPosition(3, 3));
        cp.add(new NumberButton(5, model), new RCPosition(3, 4));
        cp.add(new NumberButton(6, model), new RCPosition(3, 5));
        cp.add(new NumberButton(7, model), new RCPosition(2, 3));
        cp.add(new NumberButton(8, model), new RCPosition(2, 4));
        cp.add(new NumberButton(9, model), new RCPosition(2, 5));

    }

    public Calculator() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setTitle("Calculator");
        frame.setSize(800, 500);
        frame.setLocation(20, 20);

        stack = new Stack<>();
        inv = new JCheckBox("Inv");

        displayLabel = new JLabel("0");

        model = new CalcModelImpl();
        model.addCalcValueListener(model -> displayLabel.setText(model.toString()));

        initGUI();
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        SwingUtilities.invokeLater(calculator::show);
    }
}

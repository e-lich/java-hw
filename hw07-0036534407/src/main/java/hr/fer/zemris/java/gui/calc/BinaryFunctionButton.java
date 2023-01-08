package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;

import javax.swing.*;
import java.util.function.DoubleBinaryOperator;

public class BinaryFunctionButton extends JButton {
    public BinaryFunctionButton(String operation, CalcModelImpl model, JCheckBox inv) {
        Util.setStyle(this);
        this.setText(operation);
        inv.addActionListener(e -> this.setText(switch (this.getText()) {
            case "x^n" -> "x^(1/n)";
            case "x^(1/n)" -> "x^n";
            default -> operation;
        }));
        this.addActionListener(e -> {
            DoubleBinaryOperator operator = model.getPendingBinaryOperation();
            boolean inverse = inv.isSelected();
            if (operator != null) {
                double value = operator.applyAsDouble(model.getActiveOperand(), model.getValue());
                model.setValue(value);
                model.clearActiveOperand();
            }

            if (!operation.equals("=")) {
                model.setActiveOperand(model.getValue());
            }
            model.setPendingBinaryOperation(switch (operation) {
                case "+" -> (DoubleBinaryOperator) Double::sum;
                case "-" -> (DoubleBinaryOperator) (a, b) -> a - b;
                case "*" -> (DoubleBinaryOperator) (a, b) -> a * b;
                case "/" -> (DoubleBinaryOperator) (a, b) -> a / b;
                case "x^n" -> inverse ? (DoubleBinaryOperator) Math::pow : (DoubleBinaryOperator) (a, b) -> Math.pow(a, 1 / b);
                default -> null;
            });
        });
    }
}

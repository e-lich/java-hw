package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

import javax.swing.*;
import java.awt.*;

public class UnaryFunctionButton extends JButton {
    public UnaryFunctionButton(String operation, CalcModel model, JCheckBox inv) {
        Util.setStyle(this);
        this.setText(operation);
        this.setName(operation);
        inv.addActionListener(e -> this.setText(switch (this.getName()){
            case "sin" -> inv.isSelected() ? "arcsin" : "sin";
            case "cos" -> inv.isSelected() ? "arccos" : "cos";
            case "tan" -> inv.isSelected() ? "arctan" : "tan";
            case "ctg" -> inv.isSelected() ? "arcctg" : "ctg";
            case "log" -> inv.isSelected() ? "10^x" : "log";
            case "ln" -> inv.isSelected() ? "e^x" : "ln";
            default -> operation;
        }));
        this.addActionListener(e -> {
            boolean inverse = inv.isSelected();
            double currentNumber = model.getValue();
            model.setValue(switch (operation) {
                case "sin" -> inverse ? (float) Math.asin(currentNumber) : (float) Math.sin(currentNumber);
                case "cos" -> inverse ? (float) Math.acos(currentNumber) : (float) Math.cos(currentNumber);
                case "tan" -> inverse ? (float) Math.atan(currentNumber) : (float) Math.tan(currentNumber);
                case "ctg" -> inverse ? (float) Math.atan(1 / currentNumber) : (float) Math.tan(1 / currentNumber);
                case "log" -> inverse ? (float) Math.pow(10, currentNumber) : (float) Math.log10(currentNumber);
                case "ln" -> inverse ? (float) Math.exp(currentNumber) : (float) Math.log(currentNumber);
                case "1/x" -> 1 / currentNumber;
                default -> 0;
            });
        });
    }
}


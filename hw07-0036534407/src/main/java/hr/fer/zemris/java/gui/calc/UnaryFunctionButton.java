package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class UnaryFunctionButton extends JButton {
    public UnaryFunctionButton(String operation, String invOperation, Function<Double, Double> function, Function<Double, Double> invFunction,  CalcModel model, JCheckBox inv) {
        Util.setStyle(this);
        this.setText(operation);
        this.setName(operation);
        inv.addActionListener(e -> this.setText(inv.isSelected() ? invOperation : operation));
        this.addActionListener(e -> {
            boolean inverse = inv.isSelected();
            double currentNumber = model.getValue();
            model.setValue(inverse ? invFunction.apply(currentNumber) : function.apply(currentNumber));
        });
    }
}


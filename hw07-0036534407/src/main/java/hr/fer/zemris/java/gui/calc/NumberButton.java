package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

import javax.swing.*;

public class NumberButton extends JButton {
    public NumberButton(int number, CalcModel model) {
        Util.setStyle(this);
        this.setText(String.valueOf(number));
        this.addActionListener(e -> model.insertDigit(number));
    }
}

package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.function.DoubleBinaryOperator;

public class CalcModelImpl implements CalcModel {
    private boolean editable;
    private boolean positive;
    private String freezeString;
    private double displayedValue;
    private String displayedString;
    private double activeOperand;
    private boolean activeOperandSet;
    private DoubleBinaryOperator pendingOperation;
    private ArrayList<CalcValueListener> listeners;

    public CalcModelImpl() {
        editable = true;
        positive = true;
        displayedValue = 0;
        displayedString = "";
        freezeString = null;
        activeOperand = 0;
        activeOperandSet = false;
        pendingOperation = null;
        listeners = new ArrayList<>();
    }

    private void notifyListeners() {
        for (CalcValueListener listener : listeners) {
            listener.valueChanged(this);
        }
    }

    public void freezeValue(String value) {
        freezeString = value;
    }

    public boolean hasFrozenValue() {
        return freezeString != null;
    }

    @Override
    public void addCalcValueListener(CalcValueListener l) {
        if (l == null) throw new NullPointerException("Listener cannot be null");
        listeners.add(l);
    }

    @Override
    public void removeCalcValueListener(CalcValueListener l) {
        if (l == null) throw new NullPointerException("Listener cannot be null");
        listeners.remove(l);
    }

    @Override
    public double getValue() {
        return positive ? displayedValue : -displayedValue;
    }

    /**
     * Upisuje decimalnu vrijednost u kalkulator. Vrijednost smije
     * biti i beskonačno odnosno NaN. Po upisu kalkulator
     * postaje needitabilan.
     *
     * @param value vrijednost koju treba upisati
     */
    @Override
    public void setValue(double value) {
        displayedValue = value;

        if (value == Double.POSITIVE_INFINITY) {
            displayedString = "Infinity";
        } else if (value == Double.NEGATIVE_INFINITY) {
            displayedString = "-Infinity";
        } else if (Double.isNaN(value)) {
            displayedString = "NaN";
        } else {
            displayedString = Double.toString(value);
        }

        editable = false;

        notifyListeners();
    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    @Override
    public void clear() {
        displayedString = "";
        displayedValue = 0;
        editable = true;

        notifyListeners();
    }

    @Override
    public void clearAll() {
        clear();
        activeOperand = 0;
        activeOperandSet = false;
        pendingOperation = null;
        freezeString = null;

        notifyListeners();
    }

    @Override
    public void swapSign() throws CalculatorInputException {
        if (!editable) throw new CalculatorInputException("Calculator is not editable");

        positive = !positive;
        freezeString = null;

        notifyListeners();
    }

    @Override
    public void insertDecimalPoint() throws CalculatorInputException {
        if (!editable) throw new CalculatorInputException("Calculator is not editable");
        if (displayedString.contains(".")) throw new CalculatorInputException("Number already contains decimal point");
        if (displayedString.isEmpty()) throw new CalculatorInputException("Number is empty");

        displayedString += ".";
        displayedValue = Double.parseDouble(displayedString);
        freezeString = null;

        notifyListeners();
    }

    @Override
    public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
        if (!editable) throw new CalculatorInputException("Calculator is not editable");
        if (digit < 0 || digit > 9) throw new IllegalArgumentException("Digit must be between 0 and 9");

        if (displayedString.equals("0") || hasFrozenValue()) {
            displayedString = Integer.toString(digit);
        } else {
            displayedString += Integer.toString(digit);
        }

        try {
            displayedValue = Double.parseDouble(displayedString);
        } catch (NumberFormatException e) {
            throw new CalculatorInputException("Cannot parse number");
        }

        if (displayedValue == Double.POSITIVE_INFINITY) {
            throw new CalculatorInputException("Number is too big");
        }

        freezeString = null;

        notifyListeners();
    }

    @Override
    public boolean isActiveOperandSet() {
        return activeOperandSet;
    }

    @Override
    public double getActiveOperand() throws IllegalStateException {
        if (!activeOperandSet) throw new IllegalStateException("Active operand is not set");

        return activeOperand;
    }

    @Override
    public void setActiveOperand(double activeOperand) {
        this.activeOperand = activeOperand;
        activeOperandSet = true;
        freezeValue(String.valueOf(activeOperand));
    }

    @Override
    public void clearActiveOperand() {
        activeOperand = 0;
        activeOperandSet = false;
        editable = true;
    }

    @Override
    public DoubleBinaryOperator getPendingBinaryOperation() {
        return pendingOperation;
    }

    @Override
    public void setPendingBinaryOperation(DoubleBinaryOperator op) {
        pendingOperation = op;
        positive = true;

        notifyListeners();
    }

    @Override
    public String toString() {
        if (hasFrozenValue()) return freezeString;

        if (displayedString.isEmpty()) return positive ? "0" : "-0";

        return positive ? displayedString : "-" + displayedString;
    }
}

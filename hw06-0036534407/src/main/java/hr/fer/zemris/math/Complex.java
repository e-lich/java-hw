package hr.fer.zemris.math;

import java.util.List;

import static java.lang.Math.*;

public class Complex {
    private final double real;
    private final double imaginary;

    public static final Complex ZERO = new Complex(0,0);
    public static final Complex ONE = new Complex(1,0);
    public static final Complex ONE_NEG = new Complex(-1,0);
    public static final Complex IM = new Complex(0,1);
    public static final Complex IM_NEG = new Complex(0,-1);
    public Complex() {
        this(0,0);
    }
    public Complex(double re, double im) {
        this.real = re;
        this.imaginary = im;
    }
    public double module() {
        return sqrt(pow(real,2) + pow(imaginary,2));
    }
    public Complex multiply(Complex c) {
        return new Complex(real * c.getReal() - imaginary * c.getImaginary(),
                real * c.getImaginary() + imaginary * c.getReal());
    }
    public Complex divide(Complex c) {
        double denominator = pow(c.getReal(),2) + pow(c.getImaginary(),2);
        return new Complex((real * c.getReal() + imaginary * c.getImaginary()) / denominator,
                (imaginary * c.getReal() - real * c.getImaginary()) / denominator);
    }
    public Complex add(Complex c) {
        return new Complex(real + c.getReal(), imaginary + c.getImaginary());
    }
    public Complex sub(Complex c) {
        return new Complex(real - c.getReal(), imaginary - c.getImaginary());
    }
    public Complex negate() {
        return new Complex(-real, -imaginary);
    }

    public Complex power(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("N must be a non-negative integer!");
        }

        double module = pow(module(), n);
        double angle = n * getPolarFormAngle();
        return new Complex(module * cos(angle), module * sin(angle));
    }
    public List<Complex> root(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N must be a positive integer!");
        }

        double module = pow(module(), 1.0 / n);
        double angle = getPolarFormAngle() / n;
        List<Complex> roots = new java.util.ArrayList<>();
        for (int k = 0; k < n; k++) {
            roots.add(new Complex(module * cos(angle + 2 * k * PI), module * sin(angle + 2 * k * PI)));
        }
        return roots;
    }
    private double getPolarFormAngle() {
        double angle = atan(imaginary / real);
        if (real < 0) {
            angle += PI;
        }
        return angle;
    }

    public double getImaginary() {
        return imaginary;
    }

    public double getReal() {
        return real;
    }

    @Override
    public String toString() {
        if (imaginary == 0) {
            return String.valueOf(real);
        } else if (real == 0) {
            return imaginary + "i";
        } else if (imaginary < 0) {
            return real + " - " + -imaginary + "i";
        } else {
            return real + " + " + imaginary + "i";
        }
    }
}

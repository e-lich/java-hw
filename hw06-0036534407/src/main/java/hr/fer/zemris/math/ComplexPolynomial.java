package hr.fer.zemris.math;

import java.util.Arrays;

public class ComplexPolynomial {
    private final Complex[] factors;

    public ComplexPolynomial(Complex ... factors) {
        this.factors = factors;
    }

    public short order() {
        return (short) (factors.length - 1);
    }

    public ComplexPolynomial multiply(ComplexPolynomial p) {
        Complex[] newFactors = new Complex[order() + p.order() + 1];
        Arrays.fill(newFactors, Complex.ZERO);

        Complex[] pFactors = p.getFactors();

        for (int i = 0; i < factors.length; i++) {
            for (int j = 0; j < pFactors.length; j++) {
                newFactors[i + j] = newFactors[i + j].add(factors[i].multiply(pFactors[j]));
            }
        }

        return new ComplexPolynomial(newFactors);

    }

    public ComplexPolynomial derive() {
        Complex[] newFactors = new Complex[order()];

        for (int i = 0; i < newFactors.length; i++) {
            newFactors[i] = factors[i + 1].multiply(new Complex(i + 1, 0));
        }

        return new ComplexPolynomial(newFactors);
    }

    public Complex apply(Complex z) {
        Complex result = Complex.ZERO;
        for (int i = 0; i < factors.length; i++) {
            result = result.add(factors[i].multiply(z.power(i)));
        }

        return result;
    }

    public Complex[] getFactors() {
        return factors;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("f(z) = ");
        for (int i = factors.length - 1; i >= 0; i--) {
            sb.append("(");
            sb.append(factors[i].toString());
            sb.append(")");
            if (i != 0) {
                sb.append(" * z^");
                sb.append(i);
                sb.append(" + ");
            }
        }

        return sb.toString();
    }
}

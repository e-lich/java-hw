package hr.fer.zemris.math;

public class ComplexRootedPolynomial {
    private final Complex[] roots;
    private final Complex constant;
    public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
        this.roots = roots;
        this.constant = constant;
    }

    public Complex apply(Complex z) {
        Complex result = constant;
        for (Complex root : roots) {
            result = result.multiply(z.sub(root));
        }
        return result;
    }

    public ComplexPolynomial toComplexPolynom() {
        ComplexPolynomial result = new ComplexPolynomial(constant);
        for (Complex root : roots) {
            result = result.multiply(new ComplexPolynomial(root.negate(), Complex.ONE));
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("f(z) = ");
        sb.append(constant.toString());

        for (Complex root : roots) {
            sb.append(" * (z - ");
            sb.append(root.toString());
            sb.append(")");
        }

        return sb.toString();
    }

    public int indexOfClosestRootFor(Complex z, double threshold) {
        int index = -1;
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < roots.length; i++) {
            double distance = z.sub(roots[i]).module();
            if (distance < minDistance) {
                minDistance = distance;
                index = i;
            }
        }

        if (minDistance > threshold) {
            return -1;
        }

        return index;
    }
}

package hr.fer.zemris.fractals;

import hr.fer.zemris.math.Complex;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class Newton {
    public static void main(String[] args) {
        System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");

        ArrayList<Complex> roots = Util.getRoots();

        System.out.println("Image of fractal will appear shortly. Thank you.");

        FractalViewer.show(new NewtonProducer(roots.toArray(new Complex[0])));
    }

    public static void calculate(double reMin, double reMax, double imMin, double imMax, int width, int height,
                                 int iterMax, int yMin, int yMax, ComplexRootedPolynomial rootedPolynomial, short[] data, AtomicBoolean cancel) {
        int offset = yMin * width;
        double convergenceTreshold = 1e-3;
        ComplexPolynomial polynomial = rootedPolynomial.toComplexPolynom();

        for(int y = yMin; y <= yMax && !cancel.get(); ++y) {
            for(int x = 0; x < width; ++x) {
                double cre = (double)x / ((double)width - 1.0) * (reMax - reMin) + reMin;
                double cim = (double)(height - 1 - y) / ((double)height - 1.0) * (imMax - imMin) + imMin;
                Complex zn = new Complex(cre, cim);
                int iteration = 0;
                double module;

                do {
                    Complex numerator = polynomial.apply(zn);
                    Complex denominator = polynomial.derive().apply(zn);
                    Complex znOld = zn;
                    Complex fraction = numerator.divide(denominator);
                    zn = zn.sub(fraction);
                    module = znOld.sub(zn).module();

                    iteration++;
                } while (module > convergenceTreshold && iteration < iterMax);

                int index = rootedPolynomial.indexOfClosestRootFor(zn, 1e-3);
                data[offset++] = (short) (index + 1);
            }
        }

    }

    public static class NewtonProducer implements IFractalProducer {
        private final Complex[] roots;
        public NewtonProducer(Complex[] roots) {
            this.roots = roots;
        }

        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax,
                            int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
            ComplexRootedPolynomial rootedPolynomial = new ComplexRootedPolynomial(Complex.ONE, roots);
            ComplexPolynomial polynomial = rootedPolynomial.toComplexPolynom();
            short[] data = new short[width * height];
            int iterMax = 16 * 16 * 16;

            calculate(reMin, reMax, imMin, imMax, width, height, iterMax, 0, height-1, rootedPolynomial, data, cancel);

            observer.acceptResult(data, (short)(polynomial.order()+1), requestNo);
        }
    }
}

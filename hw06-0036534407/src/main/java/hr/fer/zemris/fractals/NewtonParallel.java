package hr.fer.zemris.fractals;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class NewtonParallel {
    public static void main(String[] args) {
        System.out.println("Welcome to Newton-Raphson parallelized fractal viewer.");

        int numberOfAvailableProcessors = Runtime.getRuntime().availableProcessors();
        int workers = numberOfAvailableProcessors;
        int tracks = 4 * numberOfAvailableProcessors;
        boolean workersDefined = false;
        boolean tracksDefined = false;

        // parse args
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.contains("--workers=")) {
                if (workersDefined) {
                    throw new IllegalArgumentException("Workers already defined.");
                }

                workers = Integer.parseInt(arg.substring(arg.indexOf("=") + 1));
                workersDefined = true;
            } else if (arg.contains("-w")) {
                if (workersDefined) {
                    throw new IllegalArgumentException("Workers already defined.");
                }

                workers = Integer.parseInt(args[++i]);
                workersDefined = true;
            } else if (arg.contains("--tracks=")) {
                if (tracksDefined) {
                    throw new IllegalArgumentException("Tracks already defined.");
                }

                tracks = Integer.parseInt(arg.substring(arg.indexOf("=") + 1));
                tracksDefined = true;
            } else if (arg.contains("-t")) {
                if (tracksDefined) {
                    throw new IllegalArgumentException("Tracks already defined.");
                }

                tracks = Integer.parseInt(args[++i]);
                tracksDefined = true;
            } else {
                throw new IllegalArgumentException("Invalid argument: " + arg);
            }
        }

        if (tracks < 1) {
            throw new IllegalArgumentException("Tracks must be greater than 0.");
        }

        if (workers < 1) {
            throw new IllegalArgumentException("Workers must be greater than 0.");
        }

        if (workers > numberOfAvailableProcessors) workers = numberOfAvailableProcessors;

        ArrayList<Complex> roots = Util.getRoots();

        System.out.println("Image of fractal will appear shortly. Thank you.");

        FractalViewer.show(new NewtonParallelProducer(roots.toArray(new Complex[0]), workers, tracks));
    }

    public static class CalculationJob implements Runnable {
        double reMin;
        double reMax;
        double imMin;
        double imMax;
        int width;
        int height;
        int yMin;
        int yMax;
        int m;
        ComplexRootedPolynomial rootedPolynomial;
        short[] data;
        AtomicBoolean cancel;
        public static CalculationJob NO_JOB = new CalculationJob();

        private CalculationJob() {
        }

        public CalculationJob(double reMin, double reMax, double imMin,
                              double imMax, int width, int height, int yMin, int yMax,
                              int m, ComplexRootedPolynomial rootedPolynomial, short[] data, AtomicBoolean cancel) {
            super();
            this.reMin = reMin;
            this.reMax = reMax;
            this.imMin = imMin;
            this.imMax = imMax;
            this.width = width;
            this.height = height;
            this.yMin = yMin;
            this.yMax = yMax;
            this.m = m;
            this.rootedPolynomial = rootedPolynomial;
            this.data = data;
            this.cancel = cancel;
        }

        @Override
        public void run() {
            Newton.calculate(reMin, reMax, imMin, imMax, width, height, m, yMin, yMax, rootedPolynomial, data, cancel);
        }
    }

    public static class NewtonParallelProducer implements IFractalProducer {
        private final Complex[] roots;
        private final int workers;
        private int tracks;

        public NewtonParallelProducer(Complex[] roots, int workers, int tracks) {
            this.roots = roots;
            this.workers = workers;
            this.tracks = tracks;
        }

        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax,
                            int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
            int maxIter = 16 * 16 * 16;
            short[] data = new short[width * height];
            if (tracks > height) {
                tracks = height;
            }

            System.out.println("Number of workers: " + workers);
            System.out.println("Number of tracks: " + tracks);

            int yPerTrack = height / tracks;
            ComplexRootedPolynomial rootedPolynomial = new ComplexRootedPolynomial(Complex.ONE, roots);

            final BlockingQueue<CalculationJob> queue = new LinkedBlockingQueue<>();

            Thread[] workersThreads = new Thread[workers];

            for(int i = 0; i < workersThreads.length; i++) {
                workersThreads[i] = new Thread(() -> {
                    while(true) {
                        CalculationJob p = null;
                        try {
                            p = queue.take();
                            if(p==CalculationJob.NO_JOB) break;
                        } catch (InterruptedException e) {
                            continue;
                        }
                        p.run();
                    }
                });
            }

            for (Thread workersThread : workersThreads) {
                workersThread.start();
            }

            for(int i = 0; i < tracks; i++) {
                int yMin = i * yPerTrack;
                int yMax = (i + 1) * yPerTrack - 1;
                if(i == tracks - 1) {
                    yMax = height - 1;
                }
                CalculationJob job = new CalculationJob(reMin, reMax, imMin, imMax, width, height, yMin, yMax, maxIter, rootedPolynomial, data, cancel);

                while(true) {
                    try {
                        queue.put(job);
                        break;
                    } catch (InterruptedException ignored) {}
                }
            }

            for(int i = 0; i < workersThreads.length; i++) {
                while(true) {
                    try {
                        queue.put(CalculationJob.NO_JOB);
                        break;
                    } catch (InterruptedException ignored) {}
                }
            }

            for (Thread workersThread : workersThreads) {
                while (true) {
                    try {
                        workersThread.join();
                        break;
                    } catch (InterruptedException ignored) {}
                }
            }

            observer.acceptResult(data, (short) maxIter, requestNo);
        }
    }
}

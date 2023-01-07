package hr.fer.zemris.fractals;

import hr.fer.zemris.math.Complex;

import java.util.ArrayList;
import java.util.Scanner;

public class Util {
    private static Complex parseComplex(String line) {
        String[] parts = line.split(" ");

        if (parts.length == 1) {
            if (parts[0].contains("i")) {
                if (parts[0].equals("i")) {
                    return Complex.IM;
                } else if (parts[0].equals("-i")) {
                    return Complex.IM_NEG;
                } else {
                    return new Complex(0, Double.parseDouble(parts[0].replace("i", "")));
                }
            } else {
                return new Complex(Double.parseDouble(parts[0]), 0);
            }
        } else {
            int k = 1;
            if (parts[1].equals("-")) {
                k = -1;
            }

            if (parts[0].contains("i")) {
                if (parts[0].equals("i")) {
                    return new Complex(k * Double.parseDouble(parts[2]), 1);
                } else if (parts[0].equals("-i")) {
                    return new Complex(k * Double.parseDouble(parts[2]), -1);
                } else {
                    return new Complex(k * Double.parseDouble(parts[2]), Double.parseDouble(parts[0].replace("i", "")));
                }
            } else {
                if (parts[2].equals("i")) {
                    return new Complex(Double.parseDouble(parts[0]), k);
                } else if (parts[2].equals("-i")) {
                    return new Complex(Double.parseDouble(parts[0]), -k);
                } else {
                    return new Complex(Double.parseDouble(parts[0]), k * Double.parseDouble(parts[2].replace("i", "")));
                }
            }
        }
    }

    public static ArrayList<Complex> getRoots() {
        System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
        System.out.print("Root 1> ");

        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        int idx = 2;

        ArrayList<Complex> roots = new ArrayList<>();

        while (!line.equals("done")) {
            System.out.print("Root " + idx + "> ");
            roots.add(parseComplex(line));
            line = sc.nextLine();
            idx++;
        }

        return roots;
    }
}

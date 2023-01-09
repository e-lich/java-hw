package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;

public class PrimListModel {
    ArrayList<Integer> primes = new ArrayList<>();

    public PrimListModel() {
        primes.add(1);
    }

    public void next() {
        int last = primes.get(primes.size() - 1);
        int next = last + 1;
        while (!isPrime(next)) {
            next++;
        }
        primes.add(next);
    }

    public boolean isPrime(int n) {
        if (n == 1) return true;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public ArrayList<Integer> getPrimes() {
        return primes;
    }
}

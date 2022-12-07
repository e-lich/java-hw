package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;

public class ArrayCollectionModificationCountDemo {
    public static void main(String[] args) {
        Collection col5 = new ArrayIndexedCollection();
        col5.add("Ivo");
        col5.add("Ana");
        col5.add("Jasna");
        ElementsGetter getter7 = col5.createElementsGetter();
        System.out.println("Jedan element: " + getter7.getNextElement());
        System.out.println("Jedan element: " + getter7.getNextElement());
        col5.clear();
        System.out.println("Jedan element: " + getter7.getNextElement());
    }
}

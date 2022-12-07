package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;

public class LinkedListCollectionModificationCountDemo {
    public static void main(String[] args) {
        System.out.println("\nDemo for LinkedListIndexedCollection modification count:");
        Collection col6 = new ArrayIndexedCollection();
        col6.add("Ivo");
        col6.add("Ana");
        col6.add("Jasna");
        ElementsGetter getter8 = col6.createElementsGetter();
        System.out.println("Jedan element: " + getter8.getNextElement());
        System.out.println("Jedan element: " + getter8.getNextElement());
        col6.clear();
        System.out.println("Jedan element: " + getter8.getNextElement());
    }
}

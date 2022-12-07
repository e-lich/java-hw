package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;
import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;

public class LinkedListCollectionElementsGetterDemo {
    public static void main(String[] args) {
        Collection col3 = new LinkedListIndexedCollection();
        Collection col4 = new LinkedListIndexedCollection();
        col3.add("Ivo");
        col3.add("Ana");
        col3.add("Jasna");
        col4.add("Jasmina");
        col4.add("Štefanija");
        col4.add("Karmela");
        ElementsGetter getter4 = col3.createElementsGetter();
        ElementsGetter getter5 = col3.createElementsGetter();
        ElementsGetter getter6 = col4.createElementsGetter();
        System.out.println("Jedan element: " + getter4.getNextElement());
        System.out.println("Jedan element: " + getter4.getNextElement());
        System.out.println("Jedan element: " + getter5.getNextElement());
        System.out.println("Jedan element: " + getter6.getNextElement());
        System.out.println("Jedan element: " + getter6.getNextElement());
    }
}

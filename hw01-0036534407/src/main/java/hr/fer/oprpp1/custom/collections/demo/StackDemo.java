package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {
    public static void main(String[] args) {
        ObjectStack stack = new ObjectStack();
        String[] elements = args[0].split(" ");

        for (String element : elements) {
            try {
                int x = Integer.parseInt(element);                                                                      // if element is a number, push to stack
                stack.push(x);
            } catch (NumberFormatException ex) {                                                                        // element is an operator, pop one or two elements from stack, perform operation and push result back on stack
                int value1;
                int value2;

                switch (element) {
                    case "+", "-",  "*", "/", "%", "bigger" -> {
                        try {
                            value1 = (int) stack.pop();
                            value2 = (int) stack.pop();
                        } catch (Exception exc) {
                            System.err.println("No number on stack!");
                            break;
                        }

                        switch (element) {
                            case "+" -> stack.push(value1 + value2);
                            case "-" -> stack.push(value1 - value2);
                            case "*" -> stack.push(value1 * value2);
                            case "/" -> {
                                if (value1 == 0) {
                                    System.err.println("Cannot divide by zero.");
                                } else {
                                    stack.push(value2 / value1);
                                }
                            }
                            case "%" -> stack.push(value1 % value2);
                            case "bigger" -> stack.push(value1 > value2 ? value1 : value2);
                        }
                    }
                    case "cubed" -> {
                        try {
                            value1 = (int) stack.pop();
                        } catch (Exception exc) {
                            System.err.println("No number on stack!");
                            break;
                        }

                        stack.push(value1 * value1 * value1);
                    }
                    default -> throw new IllegalArgumentException("Operator must be +, -, /, * or %.");
                }
            }
        }

        if (stack.size() != 1) {                                                                                        // if there is more items than the result on stack, print error, otherwise print result
            System.err.println("Stack is not empty.");
        } else {
            System.out.println("Expression evaluates to " + stack.pop() + ".");
        }
    }
}

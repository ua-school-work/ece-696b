package edu.arizona.josesosa.structural.proxy;

import edu.arizona.josesosa.structural.proxy.remote.*;

import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

public class Application {

    public static void main(String[] args) {

        MyInterface myintf = (MyInterface) Proxy.newProxyInstance(MyInterface.class.getClassLoader(),
                new Class[]{MyInterface.class}, new ProxyClass(new MyInterfaceImpl()));

        try {
            // Invoke the method
            System.out.println("--- Testing method() with no arguments ---");
            String out = myintf.method();
            System.out.println("Appl: " + out);
            System.out.println();
        } catch (Exception e) {
            handleException(e);
        }

        try {
            // Invoke the method with the allowed param
            System.out.println("--- Testing method(String) with allowed argument ---");
            String out = myintf.method("Allowed");
            System.out.println("Appl: " + out);
            System.out.println();
        } catch (Exception e) {
            handleException(e);
        }

        try {
            // Invoke the method with a forbidden param
            System.out.println("--- Testing method(String) with forbidden argument ---");
            String out = myintf.method("Secret String");
            System.out.println("Appl: " + out);
            System.out.println();
        } catch (Exception e) {
            handleException(e);
        }

        System.out.println("\n--- Further Demonstrations ---");
        
        try {
            // Another allowed parameter
            System.out.println("--- Testing with another allowed argument ---");
            String out = myintf.method("This is a perfectly fine string.");
            System.out.println("Appl: " + out);
            System.out.println();
        } catch (Exception e) {
            handleException(e);
        }

        try {
            // Forbidden parameter (case-insensitive)
            System.out.println("--- Testing with case-insensitive forbidden argument ---");
            String out = myintf.method("a top sEcReT message");
            System.out.println("Appl: " + out);
            System.out.println();
        } catch (Exception e) {
            handleException(e);
        }

        try {
            // Another forbidden parameter
            System.out.println("--- Testing with another forbidden argument ---");
            String out = myintf.method("secretive information");
            System.out.println("Appl: " + out);
            System.out.println();
        } catch (Exception e) {
            handleException(e);
        }
    }

    private static void handleException(Exception e) {
        if (e instanceof UndeclaredThrowableException) {
            System.out.println("Appl: Undeclared Error: " + e.getCause().getMessage());
        } else {
            System.out.println("Appl: Error: " + e.getMessage());
        }
        System.out.println();
    }
}
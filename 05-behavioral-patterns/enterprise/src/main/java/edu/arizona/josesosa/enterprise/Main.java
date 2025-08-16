package edu.arizona.josesosa.enterprise;

import edu.arizona.josesosa.enterprise.application.App;
import edu.arizona.josesosa.enterprise.application.AppBuilder;

import java.io.IOException;

/**
 * Application entry point moved out of App to separate concerns.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        App app = new AppBuilder()
                .withPort(8089)
                .build();
        app.start();
    }
}

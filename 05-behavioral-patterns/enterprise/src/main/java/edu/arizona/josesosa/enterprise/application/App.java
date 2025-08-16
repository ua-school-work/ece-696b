package edu.arizona.josesosa.enterprise.application;

import edu.arizona.josesosa.enterprise.http.ServerProxy;

import java.io.IOException;

/**
 * Application runtime wrapper responsible for starting the HTTP server.
 * Instances are created by {@link AppBuilder} with all dependencies wired.
 */
public class App {

    private final int port;
    private final ServerProxy serverProxy;

    /**
     * Creates a new App bound to a specific port and server proxy.
     *
     * @param port the TCP port where the server will listen
     * @param serverProxy the HTTP server abstraction to start
     */
    App(int port, ServerProxy serverProxy) {
        this.port = port;
        this.serverProxy = serverProxy;
    }

    /**
     * Starts the underlying HTTP server and logs the bound URL to stdout.
     *
     * @throws IOException if the server fails to start
     */
    public void start() throws IOException {
        serverProxy.start();
        System.out.println("Server started on http://localhost:" + port);
    }
}

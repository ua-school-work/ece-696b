package edu.arizona.josesosa.enterprise.http;

import com.sun.net.httpserver.HttpServer;
import edu.arizona.josesosa.enterprise.controller.BaseController;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Proxy responsible for hosting the HTTP server and wiring contexts to the Controller.
 */
public class ServerProxy {
    private final int port;
    private final BaseController controller;
    private final RichHttpExchange richExchange;
    private HttpServer server;

    /**
     * Create a server proxy bound to a controller and rich exchange adapter.
     * @param port TCP port to listen on
     * @param controller controller handling routes
     * @param richExchange exchange decorator bound per-request
     */
    public ServerProxy(int port, BaseController controller, RichHttpExchange richExchange) {
        this.port = port;
        this.controller = controller;
        this.richExchange = richExchange;
    }

    /**
     * Start the embedded HttpServer and register controller routes.
     * @throws IOException if the server fails to bind or start
     */
    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);

        // Wire contexts to controller methods; bind the underlying HttpExchange per request on the injected MyHttpExchange
        server.createContext("/", exchange -> {
            richExchange.setDelegate(exchange);
            controller.handleRoot();
        });
        server.createContext("/submit", exchange -> {
            richExchange.setDelegate(exchange);
            controller.handleSubmit();
        });
        server.createContext("/edit", exchange -> {
            richExchange.setDelegate(exchange);
            controller.handleEdit();
        });
        server.createContext("/delete", exchange -> {
            richExchange.setDelegate(exchange);
            controller.handleDelete();
        });

        server.setExecutor(null);
        server.start();
    }

    /**
     * Stop the server after the given delay.
     * @param delaySeconds seconds to delay before stop
     */
    public void stop(int delaySeconds) {
        if (server != null) {
            server.stop(delaySeconds);
        }
    }

    /**
     * The TCP port this server listens on.
     */
    public int getPort() {
        return port;
    }
}

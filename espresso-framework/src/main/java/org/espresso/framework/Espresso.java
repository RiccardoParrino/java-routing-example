package org.espresso.framework;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.espresso.framework.model.HttpRequest;
import org.espresso.framework.model.HttpResponse;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URISyntaxException;

public class Espresso implements HttpHandler {

    private int port;
    private HttpServer httpServer;
    private ExecutorService executorService;

    private Map<String, Consumer> routes;

    public Espresso(int port) throws IOException {
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(200);
        this.httpServer = HttpServer.create(
                new InetSocketAddress(this.port),
                0);
        this.httpServer.setExecutor(executorService);
        this.routes = new HashMap<>();
    }

    // for middleware function
    public void use(String route, Consumer middleware) {
        this.routes.put(route, middleware);
    }

    // for get endpoint
    public void get(String route, Consumer consumer) {
        this.routes.put(route, consumer);
    }

    // for post endpoint
    public void post(String route, Consumer consumer) {
        this.routes.put(route, consumer);
    }

    // for put endpoint
    public void put(String route, Consumer consumer) {
        this.routes.put(route, consumer);
    }

    // for delete endpoint
    public void delete(String route, Consumer consumer) {
        this.routes.put(route, consumer);
    }

    // for update endpoint
    public void update(String route, Consumer consumer) {
        this.routes.put(route, consumer);
    }

    public void start() {
        this.httpServer.start();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String url = exchange.getRequestURI().toString();

        HttpResponse httpResponse = new HttpResponse(exchange.getResponseBody());
        HttpRequest httpRequest = new HttpRequest(exchange.getRequestBody());

        Consumer middleware = this.routes.get(url);

        middleware.accept(httpResponse, httpRequest, middleware);

        System.out.println("System return httpResponse");
    }

}

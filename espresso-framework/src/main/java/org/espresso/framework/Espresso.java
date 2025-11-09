package org.espresso.framework;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.espresso.framework.model.HttpRequest;
import org.espresso.framework.model.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Espresso {

    private int port;
    private HttpServer httpServer;
    private ExecutorService executorService;
    private Dispatcher dispatcher;

    public Espresso(int port) throws IOException {
        this.port = port;
        this.dispatcher = new Dispatcher();
        this.executorService = Executors.newFixedThreadPool(200);
        this.httpServer = HttpServer.create(
                new InetSocketAddress(this.port),
                0);
        this.httpServer.setExecutor(this.executorService);
    }

    // for get endpoint
    public void get(String url, Process process) {
        this.dispatcher.addGetRoute(url, process);
        this.httpServer.createContext(url, this.dispatcher);
    }

    // for post endpoint
    public void post(String url, Process process) {
        this.dispatcher.addPostRoute(url, process);
        this.httpServer.createContext(url, dispatcher);
    }

    // for patch endpoint
    public void patch(String url, Process process) {
        this.dispatcher.addPatchRoute(url, process);
        this.httpServer.createContext(url, dispatcher);
    }

    // for put endpoint
    public void put(String url, Process process) {
        this.dispatcher.addPutRoute(url, process);
        this.httpServer.createContext(url, dispatcher);
    }

    // for update endpoint
    public void update(String url, Process process) {
        this.dispatcher.addUpdateRoute(url, process);
        this.httpServer.createContext(url, dispatcher);
    }

    // for delete endpoint
    public void delete(String url, Process process) {
        this.dispatcher.addDeleteRoute(url, process);
        this.httpServer.createContext(url, dispatcher);
    }

    public void listen() {
        System.out.println("Server start at address: http://localhost:" + this.port);
        this.httpServer.start();
    }

    class Dispatcher implements HttpHandler {

        private Map<String, Process> getRoutes = new HashMap<>();
        private Map<String, Process> postRoutes = new HashMap<>();
        private Map<String, Process> putRoutes = new HashMap<>();
        private Map<String, Process> patchRoutes = new HashMap<>();
        private Map<String, Process> deleteRoutes = new HashMap<>();
        private Map<String, Process> updateRoutes = new HashMap<>();

        public void addGetRoute(String url, Process process) {
            this.getRoutes.put(url, process);
        }

        public void addPostRoute(String url, Process process) {
            this.postRoutes.put(url, process);
        }

        public void addPutRoute(String url, Process process) {
            this.putRoutes.put(url, process);
        }

        public void addPatchRoute(String url, Process process) {
            this.patchRoutes.put(url, process);
        }

        public void addDeleteRoute(String url, Process process) {
            this.deleteRoutes.put(url, process);
        }

        public void addUpdateRoute(String url, Process process) {
            this.updateRoutes.put(url, process);
        }

        public HttpResponse serve(HttpRequest httpRequest) {
            HttpResponse httpResponse = new HttpResponse();

            String method = httpRequest.getMethod();

            switch (method) {
                case "GET":
                    return this.getRoutes.get(httpRequest.getUrl()).start(httpResponse, httpRequest);
                case "POST":
                    return this.postRoutes.get(httpRequest.getUrl()).start(httpResponse, httpRequest);
                case "PUT":
                    return this.putRoutes.get(httpRequest.getUrl()).start(httpResponse, httpRequest);
                case "UPDATE":
                    return this.updateRoutes.get(httpRequest.getUrl()).start(httpResponse, httpRequest);
                case "DELETE":
                    return this.deleteRoutes.get(httpRequest.getUrl()).start(httpResponse, httpRequest);
                case "PATCH":
                    return this.patchRoutes.get(httpRequest.getUrl()).start(httpResponse, httpRequest);
                default:
                    return new HttpResponse();
            }
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            preProcessRequest(exchange);
            HttpRequest httpRequest = HttpRequest.buildFrom(exchange);
            HttpResponse httpResponse = this.serve(httpRequest);
            sendResponse(exchange, httpResponse);
        }

        private void preProcessRequest(HttpExchange httpExchange) {

        }

        private void sendResponse(HttpExchange httpExchange, HttpResponse httpResponse) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            String response = mapper.writeValueAsString(httpResponse.getBody());
            httpExchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }

    }

}

// public class Espresso implements HttpHandler {

// private int port;
// private HttpServer httpServer;
// private ExecutorService executorService;

// private Map<String, Function> routes;

// public Espresso(int port) throws IOException {
// this.port = port;
// this.executorService = Executors.newFixedThreadPool(200);
// this.httpServer = HttpServer.create(
// new InetSocketAddress(this.port),
// 0);
// this.httpServer.setExecutor(executorService);
// this.routes = new HashMap<>();
// }

// // for middleware function
// public void use(String route, Function middleware) {
// this.httpServer.createContext(route, this);
// this.routes.put(route, middleware);
// }

// // for get endpoint
// public void get(String route, Function consumer) {
// this.httpServer.createContext(route, this);
// this.routes.put(route, consumer);
// }

// // // for post endpoint
// // public void post(String route, Consumer consumer) {
// // this.httpServer.createContext(route, this);
// // this.routes.put(route, consumer);
// // }

// // // for put endpoint
// // public void put(String route, Consumer consumer) {
// // this.httpServer.createContext(route, this);
// // this.routes.put(route, consumer);
// // }

// // // for delete endpoint
// // public void delete(String route, Consumer consumer) {
// // this.httpServer.createContext(route, this);
// // this.routes.put(route, consumer);
// // }

// // // for update endpoint
// // public void update(String route, Consumer consumer) {
// // this.httpServer.createContext(route, this);
// // this.routes.put(route, consumer);
// // }

// public void start() {
// System.out.println("Server start at address: http://localhost:" + this.port);
// this.httpServer.start();
// }

// @Override
// public void handle(HttpExchange exchange) throws IOException {
// String url = exchange.getRequestURI().toString();

// HttpResponse httpResponse = new HttpResponse(exchange.getResponseBody());
// HttpRequest httpRequest = new HttpRequest(exchange.getRequestBody());

// Function middleware = this.routes.get(url);

// middleware.apply(httpResponse, httpRequest);

// System.out.println("System return httpResponse");
// }

// }

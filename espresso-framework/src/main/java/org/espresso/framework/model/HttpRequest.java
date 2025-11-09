package org.espresso.framework.model;

import com.sun.net.httpserver.HttpExchange;

public class HttpRequest {
    private String url;
    private String method;
    private String header;
    private String body;

    public static HttpRequest buildFrom(HttpExchange exchange) {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(exchange.getRequestURI().toString());
        httpRequest.setMethod(exchange.getRequestMethod());
        httpRequest.setHeader(exchange.getRequestHeaders().toString());
        httpRequest.setBody(exchange.getRequestBody().toString());
        return httpRequest;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String method() {
        return this.method;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getHeader() {
        return this.header;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return this.body;
    }
}
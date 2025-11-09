package org.espresso.framework.model;

public class HttpResponse {
    private String status;
    private String header;
    private String body;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStatus() {
        return this.status;
    }

    public String getHeader() {
        return this.header;
    }

    public String getBody() {
        return this.body;
    }
}
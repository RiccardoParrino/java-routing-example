package com.example;

import java.io.IOException;

import org.espresso.framework.Espresso;

public class Main {
    public static void main(String[] args) throws IOException {
        var espresso = new Espresso(8080);

        espresso.get("/api/order/get", (res, req) -> {
            System.out.println("Hi from /api/order/get");
            return res;
        });

        espresso.post("/api/order/post", (res, req) -> {
            System.out.println("Hi from /api/order/post");
            return res;
        });

        espresso.listen();
    }
}
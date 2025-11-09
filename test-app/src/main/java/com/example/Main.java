package com.example;

import java.io.IOException;

import org.espresso.framework.Espresso;

class MyModel {
    private String msg;

    public MyModel(String msg) {
        this.msg = msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        var espresso = new Espresso(8080);

        espresso.get("/api/order/get", (res, req) -> {
            res.setBody("Hi from /api/order/get");
            return res;
        });

        espresso.post("/api/order/post", (res, req) -> {
            res.setBody(new MyModel("my msg from post"));
            return res;
        });

        espresso.listen();
    }
}
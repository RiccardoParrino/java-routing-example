package org.espresso.framework;

import org.espresso.framework.model.HttpRequest;
import org.espresso.framework.model.HttpResponse;

public interface Middleware {

    void accept(HttpResponse httpResponse, HttpRequest httpRequest, Middleware next);

}

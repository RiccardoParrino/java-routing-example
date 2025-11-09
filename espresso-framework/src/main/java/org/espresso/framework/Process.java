package org.espresso.framework;

import org.espresso.framework.model.HttpRequest;
import org.espresso.framework.model.HttpResponse;

@FunctionalInterface
public interface Process {
    public HttpResponse start(HttpResponse httpResponse, HttpRequest httpRequest);
}

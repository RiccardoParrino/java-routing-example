package org.espresso.framework;

import org.espresso.framework.model.HttpRequest;
import org.espresso.framework.model.HttpResponse;

public interface Consumer {

    void accept(HttpResponse httpResponse, HttpRequest httpRequest, Consumer next);

}

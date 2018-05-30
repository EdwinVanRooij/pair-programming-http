package io.github.edwinvanrooij.routes;

import io.github.edwinvanrooij.Message;
import io.github.edwinvanrooij.bus.MessageBus;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class JoinEditorRoute implements Route {

    private MessageBus bus;

    public JoinEditorRoute(MessageBus bus) {
        this.bus = bus;
    }

    @Override
    public Object handle(Request request, Response response) {
        // Retrieve the code
        String code = request.queryParams("code");

        // Check whether or not the queue exists
        if (!bus.exchangeExists(code)) {
            // Queue does not exist
            response.status(400);
            return Message.generateJson(String.format("Queue with code '%s' does not exist. Please check again.", code));
        }

        // Queue exists, return success
        response.status(200);
        return Message.generateJson("Success!");
    }
}

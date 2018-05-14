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
        // Retrieve the ID
        Message m = Message.fromJson(request.body());
        String id = m.getMessage();

        // Check whether or not the queue exists
        if (!bus.queueExists(id)) {
            // Queue does not exist
            response.status(400);
            return Message.generateJson(String.format("Queue with ID '%s' does not exist. Please check again.", id));
        }

        // Queue exists, return success
        response.status(200);
        return Message.generateJson(generateUniqueId());
    }


    /**
     * Securely generates a random ID.
     * https://stackoverflow.com/questions/24876188/how-big-is-the-chance-to-get-a-java-uuid-randomuuid-collision
     */
    private static String generateUniqueId() {
        return String.valueOf(UUID.randomUUID());
    }
}

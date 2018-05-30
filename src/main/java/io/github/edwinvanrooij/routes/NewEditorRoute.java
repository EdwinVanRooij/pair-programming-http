package io.github.edwinvanrooij.routes;

import io.github.edwinvanrooij.Message;
import io.github.edwinvanrooij.bus.MessageBus;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.UUID;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NewEditorRoute implements Route {

    private MessageBus bus;

    public NewEditorRoute(MessageBus bus) {
        this.bus = bus;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        try {
            // Generate a unique ID
            String id = generateUniqueId();

            // Create new Queue based on this ID
            bus.createNewExchange(id);

            // Everything went well, return success
            response.status(200);
            return Message.generateJson(id);

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
            // Something went wrong, return failure with explanation
            response.status(500);
            return Message.generateJson(e.getMessage());
        }
    }


    /**
     * Securely generates a random ID.
     * https://stackoverflow.com/questions/24876188/how-big-is-the-chance-to-get-a-java-uuid-randomuuid-collision
     */
    private static String generateUniqueId() {
        return String.valueOf(UUID.randomUUID());
    }
}

package io.github.edwinvanrooij;

import io.github.edwinvanrooij.bus.MessageBus;
import io.github.edwinvanrooij.routes.JoinEditorRoute;
import io.github.edwinvanrooij.routes.NewEditorRoute;
import spark.Filter;

import static spark.Spark.*;

public class App {

    private static MessageBus bus;

    public static void main(String[] args) {
        bus = new MessageBus();

        enableCORS();

        // Respond to new editor request
        get("/newEditor", new NewEditorRoute(bus));

        // Respond to join editor request
        get("/joinEditor", new JoinEditorRoute(bus));
    }

    private static void enableCORS() {
        before((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method", "*");
            response.header("Access-Control-Allow-Headers", "*");
        });
    }
}

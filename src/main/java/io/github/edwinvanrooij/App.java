package io.github.edwinvanrooij;

import io.github.edwinvanrooij.bus.MessageBus;
import io.github.edwinvanrooij.routes.JoinEditorRoute;
import io.github.edwinvanrooij.routes.NewEditorRoute;
import spark.Filter;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.put;

public class App {

    private static MessageBus bus;

    public static void main(String[] args) {
        bus = new MessageBus();

        // Respond to new editor request
        get("/newEditor", new NewEditorRoute(bus));

        // Respond to join editor request
        put("/joinEditor", new JoinEditorRoute(bus));

        after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET");
            response.header("Access-Control-Allow-Methods", "PUT");
        });
    }
}

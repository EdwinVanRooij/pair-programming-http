package io.github.edwinvanrooij;

import io.github.edwinvanrooij.bus.MessageBus;
import io.github.edwinvanrooij.routes.NewEditorRoute;
import spark.Spark;

public class App {

    private static MessageBus bus;

    public static void main(String[] args) {
        bus = new MessageBus();

        // Respond to new editor request
        Spark.get("/newEditor", new NewEditorRoute(bus));
    }
}

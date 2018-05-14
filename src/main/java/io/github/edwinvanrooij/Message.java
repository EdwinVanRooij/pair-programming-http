package io.github.edwinvanrooij;

import com.google.gson.Gson;

public class Message {
    private static Gson gson = new Gson();

    private String message;

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String generateJson(String message) {
        return gson.toJson(new Message(message));
    }

    public static Message fromJson(String body) {
        return gson.fromJson(body, Message.class);
    }
}

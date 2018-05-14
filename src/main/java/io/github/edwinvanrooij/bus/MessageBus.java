package io.github.edwinvanrooij.bus;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MessageBus {

    public static String QUEUE_NAME = "dpi_pair_programming_queue";

    public MessageBus() {

    }


    public void produceMessage(String json) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, json.getBytes());
            System.out.println(String.format("Sent '%s' to queue.", json));

            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void consumeMessage(MessageHandler handler) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            System.out.println("Waiting for messages. To exit press CTRL+C");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    handler.handleMessage(message);
                }
            };
            channel.basicConsume(QUEUE_NAME, true, consumer);

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void createNewQueue(String queueName) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);

        channel.close();
        connection.close();
    }

    public boolean queueExists(String queueName) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            AMQP.Queue.DeclareOk result = channel.queueDeclarePassive(queueName);
            System.out.println(String.format("Result of queue exists: %s", result));
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            // Queue does not exist if we get here
            return false;
        }
        // Got past the check, the queue exists
        return true;
    }
}

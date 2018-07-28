package io.github.edwinvanrooij.bus;

import com.rabbitmq.client.*;
import io.github.edwinvanrooij.Const;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MessageBus {

    public MessageBus() {

    }

    public void createNewExchange(String exchangeName) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Const.IP);

//        factory.setUsername("guest");
//        factory.setPassword("guest");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(exchangeName, "fanout");

//        String message = getMessage(argv);

//        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
//        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }

    public boolean exchangeExists(String exchangeName) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(Const.IP);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

//            AMQP.Queue.DeclareOk result = channel.queueDeclarePassive(exchangeName);
            AMQP.Exchange.DeclareOk result = channel.exchangeDeclarePassive(exchangeName);
            System.out.println(String.format("Result of exchange exists: %s", result));
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

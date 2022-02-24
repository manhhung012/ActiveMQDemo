package receiver;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        Connection connection = connectionFactory.createConnection("admin", "admin");

        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = null;

        destination = session.createQueue("hung-jms-queue");

        MessageConsumer messageConsumer = session.createConsumer(destination);

        System.out.println("Start receiving messages...");
        String body;

        do {
            Message msg = messageConsumer.receive();
            body = ((TextMessage) msg).getText();
            System.out.println("Received = " + body);
        } while (!body.equalsIgnoreCase("close"));

        connection.close();
        System.exit(1);
    }
}

package sender;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Producer {
    public static void main(String[] args) throws JMSException, IOException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        Connection connection = connectionFactory.createConnection("admin", "admin");

        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue("hung-jms-queue");

        MessageProducer messageProducer = session.createProducer(destination);

        System.out.println("Start sending messages...");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));) {
            String messages;
            do {
                System.out.print("Enter messages....: ");
                messages = br.readLine().trim();

                TextMessage msg = session.createTextMessage(messages);

                messageProducer.send(msg);

            } while (!messages.equalsIgnoreCase("close"));
        }
        connection.close();
        System.exit(1);
    }

}

package se.hw.kafka.demo.engine;

import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import se.hw.kafka.demo.model.Order;

@Service
public class OrderProducer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private static final String TOPIC = "order";

    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;


    public void sendMessage(Order order) {
 
        logger.info("sending data='{}' to topic='{}'", order, TOPIC);

        Message<Order> message = MessageBuilder
                .withPayload(order)
                .setHeader(KafkaHeaders.TOPIC, TOPIC)
                .build();
 
        kafkaTemplate.send(message);

    }
}

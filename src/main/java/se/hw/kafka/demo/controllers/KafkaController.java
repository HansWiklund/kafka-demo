package se.hw.kafka.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import se.hw.kafka.demo.engine.OrderProducer;
import se.hw.kafka.demo.model.Order;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

    private final OrderProducer producer;

    @Autowired
    KafkaController(OrderProducer producer) {
        this.producer = producer;
    }

    @PostMapping(value = "/publish", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendMessageToKafkaTopic(@RequestBody Order order) {
        this.producer.sendMessage(order);
    }
    
}

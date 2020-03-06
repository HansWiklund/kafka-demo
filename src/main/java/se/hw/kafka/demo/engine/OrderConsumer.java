package se.hw.kafka.demo.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import se.hw.kafka.demo.model.Order;

import java.io.IOException;

@Service
public class OrderConsumer {

    private final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);
    private String latestId;
    
    public String getLatestId() {
		return latestId;
	}

	@KafkaListener(topics = "order")
    public void consume(@Payload Order data,
                        @Headers MessageHeaders headers) {
		
		latestId=data.getId();
		
        logger.info("received data='{}'", data.getId());

        headers.keySet().forEach(key -> {
            logger.info("{}: {}", key, headers.get(key));
        });
    }
}

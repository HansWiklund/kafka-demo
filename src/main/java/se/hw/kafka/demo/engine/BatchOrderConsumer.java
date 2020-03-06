package se.hw.kafka.demo.engine;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.messaging.Message;

import se.hw.kafka.demo.model.Order;

@Service
public class BatchOrderConsumer {

    private final Logger logger = LoggerFactory.getLogger(BatchOrderConsumer.class);
    private String latestId;
    
    public String getLatestId() {
		return latestId;
	}

	@KafkaListener(topics = "batch-order",
            containerFactory = "kafkaListenerContainerFactory2" ,
            groupId = "com.groupid2")
    public void consume(@Payload List<Message<Order>> messages
                        ) {		
        logger.info("received data='{}'", messages.size());

        for (int i = 0; i < messages.size(); i++) {
        	Order message = messages.get(i).getPayload();
        	MessageHeaders headers = messages.get(i).getHeaders();
        	if(i==0)
        		latestId=(String) headers.get(KafkaHeaders.RECEIVED_MESSAGE_KEY);
        }
    }
}

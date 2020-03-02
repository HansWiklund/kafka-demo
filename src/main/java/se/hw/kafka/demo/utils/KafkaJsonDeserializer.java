package se.hw.kafka.demo.utils;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import se.hw.kafka.demo.engine.OrderConsumer;

public class KafkaJsonDeserializer<T> implements Deserializer {

    private final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);

    private Class <T> type;

    public KafkaJsonDeserializer(Class type) {
        this.type = type;
    }

    @Override
    public Object deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        T obj = null;
        try {
            obj = mapper.readValue(bytes, type);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return obj;
    }

    @Override
    public void close() {

    }

	@Override
	public void configure(Map configs, boolean isKey) {
		// TODO Auto-generated method stub
		
	}
}

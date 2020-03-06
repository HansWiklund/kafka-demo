package se.hw.kafka.demo;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Map;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import se.hw.kafka.demo.engine.OrderConsumer;
import se.hw.kafka.demo.engine.OrderProducer;
import se.hw.kafka.demo.model.Item;
import se.hw.kafka.demo.model.Order;
import se.hw.kafka.demo.model.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class SpringKafkaReceiverTest {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(SpringKafkaReceiverTest.class);

  private static String TOPIC = "order";

  @Autowired
  private OrderConsumer receiver;

  @Autowired
  private OrderProducer sender;

  @ClassRule
  public static EmbeddedKafkaRule embeddedKafka =
      new EmbeddedKafkaRule(1, true, TOPIC);

  @Before
  public void setUp() throws Exception {

  }

  @Test
  public void testReceive() throws Exception {
	  
      Order order = new Order();
      order.setId("No111");
      
      Item item = new Item();
             
      Product product = new Product();
      product.setProductId("1");
      product.setName("Foo");
      
      item.setProduct(product);
      item.setPrice(1000L);
      
      order.getItems().add(item);
      
    // send the message
      sender.sendMessage(order);

    Thread.sleep(1000L);
    // check that the message was received
    assertThat(receiver.getLatestId()).isEqualTo("No111"); // TODO
  }
}

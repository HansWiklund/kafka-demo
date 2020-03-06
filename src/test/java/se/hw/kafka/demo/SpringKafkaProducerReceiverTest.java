package se.hw.kafka.demo;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import se.hw.kafka.demo.engine.OrderConsumer;
import se.hw.kafka.demo.engine.OrderProducer;
import se.hw.kafka.demo.model.Item;
import se.hw.kafka.demo.model.Order;
import se.hw.kafka.demo.model.Product;
import se.hw.kafka.demo.utils.RestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext
public class SpringKafkaProducerReceiverTest {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(SpringKafkaProducerReceiverTest.class);

  @LocalServerPort
  int randomServerPort;

  @Autowired
  RestClient client;
  
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
	        
    // send the message
      sender.sendMessage(createOrder("No111"));

    Thread.sleep(1000L);
    // check that the message was received
    assertThat(receiver.getLatestId()).isEqualTo("No111"); // TODO
  }
  
  @Test
  public void testRestCallToProducer() throws Exception {

	  /*
	  WebTestClient testClient = WebTestClient
			  .bindToServer()
			  .baseUrl("http://localhost:" + randomServerPort)
			  .build();

	  testClient
	    .post()
	    .uri(uri -> uri.path("/kafka/publish").build())
        .accept(MediaType.APPLICATION_JSON)
	    .contentType(MediaType.APPLICATION_JSON)
	    .body( BodyInserters.fromValue(createOrder("No112")) )
	    .exchange();
	   */
	  client.post(createOrder("No112"));
      Thread.sleep(1000L);
      // check that the message was received
      assertThat(receiver.getLatestId()).isEqualTo("No112"); // TODO
  }
  
  private Order createOrder(String id) {
      Order order = new Order();
      order.setId(id);
      
      Item item = new Item();
             
      Product product = new Product();
      product.setProductId("1");
      product.setName("Foo");
      
      item.setProduct(product);
      item.setPrice(1000L);
      
      order.getItems().add(item);
      
      return order;
  }
}

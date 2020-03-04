package se.hw.kafka.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import se.hw.kafka.demo.engine.OrderProducer;
import se.hw.kafka.demo.model.Item;
import se.hw.kafka.demo.model.Order;
import se.hw.kafka.demo.model.Product;
import se.hw.kafka.demo.utils.KafkaJsonSerializer;
import se.hw.kafka.demo.utils.RestClient;

@SpringBootApplication(scanBasePackages= {"se.hw.kafka.demo", "se.hw.kafka.demo.*"})
public class SpringBootWithKafkaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWithKafkaApplication.class, args);
	}

    @Autowired
    private OrderProducer sender;

    @Override
    public void run(String... strings) throws Exception {
    	
        Order order = new Order();
        order.setId("No1");
        
        Item item = new Item();
               
        Product product = new Product();
        product.setProductId("1");
        product.setName("Foo");
        
        item.setProduct(product);
        item.setPrice(1000L);
        
        order.getItems().add(item);
        
        sender.sendMessage(order);
        
        KafkaJsonSerializer j = new KafkaJsonSerializer();
        byte[] b = j.serialize(null, order);
        System.out.println(">>>>>>>" + new String(b));
        
        order.setId("No2");

        RestClient client = new RestClient();
        client.post(order);
    }
}


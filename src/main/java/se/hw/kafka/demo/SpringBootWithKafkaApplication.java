package se.hw.kafka.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import se.hw.kafka.demo.engine.OrderProducer;
import se.hw.kafka.demo.model.Item;
import se.hw.kafka.demo.model.Order;
import se.hw.kafka.demo.model.Product;

@SpringBootApplication
public class SpringBootWithKafkaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWithKafkaApplication.class, args);
	}
	
    @Autowired
    private OrderProducer sender;

    @Override
    public void run(String... strings) throws Exception {
        Order order = new Order();
        Product product = new Product();
        Item item = new Item();
        
        
        product.setProductId("1");
        product.setName("Foo");
        
        item.setProduct(product);
        item.setPrice(1000L);
        
        order.getItems().add(item);
        
        sender.sendMessage(order);
    }
}


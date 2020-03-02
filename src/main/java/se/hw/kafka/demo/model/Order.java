package se.hw.kafka.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
	private String id;
	private List<Item> items = new ArrayList<>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	

}

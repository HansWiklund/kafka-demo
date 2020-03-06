package se.hw.kafka.demo.utils;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import se.hw.kafka.demo.model.Order;

@Service
public class RestClient {

    @Value("${server.port}")
    private String port;
    
    @Autowired
    Environment environment;
 
	WebClient webClient;

	// @PostConstruct will not work here since local.server.port has not been assigned yet.
	private void initClient() {
		
		if("0".equals(port))
			port = environment.getProperty("local.server.port");
		
		webClient = WebClient
				  .builder()
				    .baseUrl("http://localhost:" + port)
				    .defaultCookie("cookieKey", "cookieValue")
				    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				    .defaultUriVariables(Collections.singletonMap("url", "http://localhost:" + port))
				  .build();		
	}
	
	public void post(Order payload) {
		
		if(webClient == null)		
			initClient();
		
		webClient
	    .post()
	    .uri(uri -> uri.path("/kafka/publish").build())
        .accept(MediaType.APPLICATION_JSON)
	    .contentType(MediaType.APPLICATION_JSON)
	    .body( BodyInserters.fromValue(payload) )
	    .exchange()
        .flatMap(clientResponse -> clientResponse.toEntity(String.class))
        .block();
	}

}

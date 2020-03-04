package se.hw.kafka.demo.utils;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import se.hw.kafka.demo.model.Order;

public class RestClient {

    @Value("${server.port}")
    private String port;
    
	WebClient webClient = WebClient
			  .builder()
			    .baseUrl("http://localhost:" + port)
			    .defaultCookie("cookieKey", "cookieValue")
			    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			    .defaultUriVariables(Collections.singletonMap("url", "http://localhost:9000"))
			  .build();

	public void post(Order payload) {
		webClient
	    .post()
	    .uri(uri -> uri.path("/kafka/publish").build())
        .accept(MediaType.APPLICATION_JSON)
	    .contentType(MediaType.APPLICATION_JSON)
	    .body( BodyInserters.fromObject(payload) )
	    .exchange()
        .flatMap(clientResponse -> clientResponse.toEntity(String.class))
        .block();
	}
}

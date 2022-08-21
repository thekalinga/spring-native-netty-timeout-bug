package com.acme;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import static io.netty.handler.logging.LogLevel.DEBUG;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static reactor.netty.transport.logging.AdvancedByteBufFormat.TEXTUAL;

@Component
@SuppressWarnings("unused") // since we are dealing with a component
public class ApiAccessor {

  public Mono<GetResponse> access() {
    final var reactorHttpClient = HttpClient.create()
        .wiretap(getClass().getCanonicalName(), DEBUG, TEXTUAL, UTF_8); // capture messages over wire
    final var reactorClientHttpConnector = new ReactorClientHttpConnector(reactorHttpClient);

    WebClient webClient = WebClient.builder().baseUrl("https://httpbin.org")
        .clientConnector(reactorClientHttpConnector)
        .build();

    /*
    curl -X GET "https://httpbin.org/get" -H "accept: application/json"
     */
    return webClient
        .method(GET)
        .uri("/get")
        .accept(APPLICATION_JSON)
        .retrieve()
        .bodyToMono(GetResponse.class);
  }
}

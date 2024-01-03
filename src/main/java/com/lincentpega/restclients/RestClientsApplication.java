package com.lincentpega.restclients;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class RestClientsApplication {

    private final Logger LOG = LogManager.getLogger(RestClientsApplication.class);

    final List<String> names = List.of("Pens", "Igor", "Vladimir");

    public static void main(String[] args) {
        SpringApplication.run(RestClientsApplication.class, args);
    }


    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:8080/api/test")
                .build();
    }

    private CompletableFuture<Response> fetchAsync(RestClient restClient, String name) {
        return CompletableFuture.supplyAsync(
                () -> {
                    final Response resp = performRequest(restClient, name);
                    LOG.info(System.nanoTime());
                    return resp;
                }
        );
    }

    private static Response performRequest(RestClient restClient, String name) {
        return restClient.get()
                .uri("/hello", uriBuilder -> uriBuilder.queryParam("name", name).build())
                .retrieve()
                .body(Response.class);
    }

    @Bean
    public CommandLineRunner run(RestClient restClient) {
        return args -> {
            final List<CompletableFuture<Response>> futures = names.stream()
                    .map(name -> fetchAsync(restClient, name))
                    .toList();
            final CompletableFuture<Void> allOff = CompletableFuture.allOf(futures.toArray(new CompletableFuture[]{}));
            final CompletableFuture<List<Response>> results = allOff.thenApply(unused -> futures.stream()
                    .map(CompletableFuture::join)
                    .toList());
            final List<Response> responses = results.get();
            responses.forEach(LOG::info);
        };
    }
}

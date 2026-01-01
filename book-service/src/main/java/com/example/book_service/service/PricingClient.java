package com.example.book_service.service;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PricingClient {

    private final RestTemplate rest;
    private final String baseUrl;

    public PricingClient(RestTemplate rest,
                         @Value("${pricing.base-url}") String baseUrl) {
        this.rest = rest;
        this.baseUrl = baseUrl;
    }

    @Retry(name = "pricing")
    @CircuitBreaker(name = "pricing", fallbackMethod = "fallbackPrice")
    public double getPrice(long bookId) {
        String url = baseUrl + "/api/prices/" + bookId;
        Double price = rest.getForObject(url, Double.class);
        return price == null ? 0.0 : price;
    }

    // signature obligatoire : mêmes paramètres + Throwable
    public double fallbackPrice(long bookId, Throwable ex) {
        return 0.0;
    }
}
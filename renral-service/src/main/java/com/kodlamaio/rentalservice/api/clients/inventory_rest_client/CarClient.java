package com.kodlamaio.rentalservice.api.clients.inventory_rest_client;

import com.kodlamaio.commonpackage.utils.dto.responses.ClientResponse;
import com.kodlamaio.commonpackage.utils.dto.responses.GetCarResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;


@Service
@FeignClient(name = "inventory-service", fallback = CarClientFallback.class)
public interface CarClient {

    @GetMapping(value = "/api/cars/check-car-available/{carId}")
    ClientResponse checkIfCarAvailableInInventory(@PathVariable UUID carId);

    @GetMapping(value = "/api/cars/{carId}")
    GetCarResponse getCarById(@PathVariable UUID carId);
}

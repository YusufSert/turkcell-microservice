package com.kodlamaio.rentalservice.api.clients.inventory_rest_client;

import com.kodlamaio.commonpackage.utils.dto.responses.ClientResponse;
<<<<<<< HEAD
=======
import com.kodlamaio.commonpackage.utils.dto.responses.GetCarResponse;
>>>>>>> origin/main
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;


<<<<<<< HEAD
@FeignClient(name = "inventory-service", fallback = CarClientFallback.class)
@Service
=======
@Service
@FeignClient(name = "inventory-service", fallback = CarClientFallback.class)
>>>>>>> origin/main
public interface CarClient {

    @GetMapping(value = "/api/cars/check-car-available/{carId}")
    ClientResponse checkIfCarAvailableInInventory(@PathVariable UUID carId);
<<<<<<< HEAD
=======

    @GetMapping(value = "/api/cars/{carId}")
    GetCarResponse getCarById(@PathVariable UUID carId);
>>>>>>> origin/main
}

package com.kodlamaio.rentalservice.api.clients.inventory_rest_client;
import com.kodlamaio.commonpackage.utils.dto.responses.ClientCarResponse;
import com.kodlamaio.commonpackage.utils.dto.responses.ClientSuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;


@FeignClient(name = "inventory-service", fallback = CarClientFallback.class)
@Service
public interface CarClient {

    @GetMapping(value = "/api/cars/check-car-available/{carId}")
    ClientSuccessResponse checkIfCarAvailableInInventory(@PathVariable UUID carId);


    @GetMapping(value = "/api/cars/{carId}")
    ClientCarResponse getCarById(@PathVariable UUID carId);
}

package com.kodlamaio.rentalservice.api.clients;

import com.kodlamaio.commonpackage.utils.dto.ClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class CarClientFallback implements CarClient {

    public ClientResponse checkIfCarAvailableInInventory(UUID carId) {
        log.info("Couldn't connect to inventory server not working properly !");
        return new ClientResponse(false, "Couldn't connect to inventory server");
    }
}

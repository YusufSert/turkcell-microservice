package com.kodlamaio.rentalservice.api.clients.inventory_rest_client;

import com.kodlamaio.commonpackage.utils.dto.responses.ClientCarResponse;
import com.kodlamaio.commonpackage.utils.dto.responses.ClientSuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Slf4j
@Component
public class CarClientFallback implements CarClient {

    public ClientSuccessResponse checkIfCarAvailableInInventory(UUID carId) {
        log.info("Couldn't connect to inventory server not working properly !");
        return new ClientSuccessResponse(false, "Couldn't connect to inventory server");
    }

    @Override
    public ClientCarResponse getCarById(UUID carId) {
        return null;
    }

}

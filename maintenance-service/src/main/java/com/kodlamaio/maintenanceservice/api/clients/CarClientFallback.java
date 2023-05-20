package com.kodlamaio.maintenanceservice.api.clients;

import com.kodlamaio.commonpackage.utils.dto.ClientResponse;

import java.util.UUID;

public class CarClientFallback  implements CarClient{

    @Override
    public ClientResponse checkIfCarAvailable(UUID carId) {
        return null;
    }
}

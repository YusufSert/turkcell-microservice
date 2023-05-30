package com.kodlamaio.maintenanceservice.api.clients;

import com.kodlamaio.commonpackage.utils.dto.responses.ClientSuccessResponse;

import java.util.UUID;

public class CarClientFallback  implements CarClient{

    @Override
    public ClientSuccessResponse checkIfCarAvailable(UUID carId) {
        return null;
    }
}

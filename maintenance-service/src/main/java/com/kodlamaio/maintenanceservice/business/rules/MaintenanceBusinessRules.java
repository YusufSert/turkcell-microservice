package com.kodlamaio.maintenanceservice.business.rules;

import com.kodlamaio.commonpackage.utils.dto.ClientResponse;
import com.kodlamaio.commonpackage.utils.exceptions.BusinessException;
import com.kodlamaio.maintenanceservice.api.clients.CarClient;
import com.kodlamaio.maintenanceservice.entities.State;
import com.kodlamaio.maintenanceservice.repository.MaintenanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MaintenanceBusinessRules {
    private final MaintenanceRepository repository;
    private final CarClient carClient;


    public void checkIfMaintenanceExists(UUID id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("MAINTENANCE_NOT_EXISTS");
        }
    }

    public void checkIfCarUnderMaintenance(UUID carId) {
        if (repository.existsByCarIdAndIsCompletedIsFalse(carId)) {
            throw new BusinessException("CAR_IS_CURRENTLY_UNDER_MAINTENANCE");
        }
    }

    public void isRegisteredAndCompletedForMaintenance(UUID carId) {
        if (!repository.existsByCarIdAndIsCompletedIsFalse(carId)) {
            throw new BusinessException("CAR_NOT_REGISTERED_FOR_MAINTENANCE"); // carId - false
        }
    }

    public void checkCarAvailabilityForMaintenance(UUID carId) {
       ClientResponse response = carClient.checkIfCarAvailable(carId);
       if(!response.isSuccess()){
           throw new BusinessException("Car not availability for maintenance");
       }
    }
}

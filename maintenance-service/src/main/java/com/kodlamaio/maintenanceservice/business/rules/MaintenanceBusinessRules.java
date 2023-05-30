package com.kodlamaio.maintenanceservice.business.rules;

import com.kodlamaio.commonpackage.utils.dto.responses.ClientSuccessResponse;
import com.kodlamaio.commonpackage.utils.exceptions.BusinessException;
import com.kodlamaio.maintenanceservice.api.clients.CarClient;
import com.kodlamaio.maintenanceservice.entities.Maintenance;
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
            throw new BusinessException("There is no any maintenance record found by this id");
        }
    }

    public void checkIfCarUnderMaintenance(UUID carId) {
        if (repository.existsByCarIdAndCompletedIsFalse(carId)) {
            throw new BusinessException("CAR_IS_CURRENTLY_UNDER_MAINTENANCE");
        }
    }

    public void checkIfCarRegisteredAndNotCompleted(UUID carId) {
        if (!repository.existsByCarIdAndCompletedIsFalse(carId)) {
            throw new BusinessException("Car is not currently in maintenance."); // carId - false
        }
    }

    public void checkCarAvailabilityForMaintenance(UUID carId) {
       ClientSuccessResponse response = carClient.checkIfCarAvailable(carId);
       if(!response.isSuccess()){
           throw new BusinessException(response.getMessage());
       }
    }


    public void isDeletable(UUID id) {
        checkIfMaintenanceExists(id);
        Maintenance maintenance = repository.findById(id).orElseThrow();
        if(!maintenance.isCompleted()) {
            throw new BusinessException("Car hasn't complete his maintenance !");
        }
    }
}

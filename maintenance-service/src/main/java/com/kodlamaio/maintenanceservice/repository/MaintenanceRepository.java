package com.kodlamaio.maintenanceservice.repository;

import com.kodlamaio.maintenanceservice.entities.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface MaintenanceRepository extends JpaRepository<Maintenance, UUID> {
    Maintenance findMaintenanceByCarIdAndCompletedIsFalse(UUID carId);
    boolean existsByCarIdAndCompletedIsFalse(UUID carId);
}

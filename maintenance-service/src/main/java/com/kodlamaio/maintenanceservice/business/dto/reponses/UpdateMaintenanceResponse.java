package com.kodlamaio.maintenanceservice.business.dto.reponses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMaintenanceResponse {
    private UUID id;
    private UUID carId;
    private String information;
    private boolean completed;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}

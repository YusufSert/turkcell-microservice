package com.kodlamaio.maintenanceservice.business.concretes;

import com.kodlamaio.commonpackage.events.maintenance.MaintenanceCreatedEvent;
import com.kodlamaio.commonpackage.events.maintenance.MaintenanceReturnEvent;
import com.kodlamaio.commonpackage.kafka.producer.KafkaProducer;
import com.kodlamaio.commonpackage.utils.constants.Paths;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.maintenanceservice.business.abstracts.MaintenanceService;
import com.kodlamaio.maintenanceservice.business.dto.reponses.CreateMaintenanceResponse;
import com.kodlamaio.maintenanceservice.business.dto.reponses.GetAllMaintenancesResponse;
import com.kodlamaio.maintenanceservice.business.dto.reponses.GetMaintenanceResponse;
import com.kodlamaio.maintenanceservice.business.dto.reponses.UpdateMaintenanceResponse;
import com.kodlamaio.maintenanceservice.business.dto.requests.CreateMaintenanceRequest;
import com.kodlamaio.maintenanceservice.business.dto.requests.UpdateMaintenanceRequest;
import com.kodlamaio.maintenanceservice.business.rules.MaintenanceBusinessRules;
import com.kodlamaio.maintenanceservice.entities.Maintenance;
import com.kodlamaio.maintenanceservice.repository.MaintenanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
@Service
@AllArgsConstructor
public class MaintenanceManager implements MaintenanceService {
    private final MaintenanceRepository repository;
    private final ModelMapperService mapper;
    private final MaintenanceBusinessRules rules;
    private final KafkaProducer producer;


    @Override
    public List<GetAllMaintenancesResponse> getAll() {
        List<Maintenance>maintenances = repository.findAll();
        List<GetAllMaintenancesResponse> maintenancesResponses = maintenances
                .stream().map(maintenance -> mapper.forResponse().map(maintenance, GetAllMaintenancesResponse.class)).toList();
        return maintenancesResponses; }

    @Override
    public GetMaintenanceResponse getById(UUID id) {
        var maintenance = repository.findById(id).orElseThrow();
        var maintenancesResponses = mapper.forResponse().map(maintenance,GetMaintenanceResponse.class);
        return maintenancesResponses;
    }

    @Override
    public GetMaintenanceResponse returnCarFromMaintenance(UUID carId) {
        rules.isRegisteredAndCompletedForMaintenance(carId);
        Maintenance maintenance = repository.findMaintenanceByCarIdAndIsCompletedFalse(carId);
        maintenance.setEndDate(LocalDateTime.now());
        maintenance.setCompleted(true);
        sendKafkaMaintenanceReturnEvent(carId);
        return mapper.forResponse().map(maintenance, GetMaintenanceResponse.class);
    }


    @Override
    public CreateMaintenanceResponse add(CreateMaintenanceRequest request) {
        rules.checkCarAvailabilityForMaintenance(request.getCarId());
        var maintenance = mapper.forRequest().map(request, Maintenance.class);
        maintenance.setCompleted(false);
        maintenance.setStartDate(LocalDateTime.now());
        maintenance.setId(UUID.randomUUID());
        repository.save(maintenance);
        sendKafkaMaintenanceCreatedEvent(maintenance.getCarId());
        return mapper.forResponse().map(maintenance, CreateMaintenanceResponse.class);
    }

    @Override
    public UpdateMaintenanceResponse update(UUID id, UpdateMaintenanceRequest request) {
        rules.checkIfMaintenanceExists(id);
        Maintenance maintenance = mapper.forRequest().map(request, Maintenance.class);
        maintenance.setId(id);
        repository.save(maintenance);
        return mapper.forResponse().map(maintenance, UpdateMaintenanceResponse.class);
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfMaintenanceExists(id);
        repository.deleteById(id);
    }

    private void sendKafkaMaintenanceCreatedEvent(UUID carID) {
        producer.sendMessage(new MaintenanceCreatedEvent(carID), "maintenance-created");
    }

    private void sendKafkaMaintenanceReturnEvent(UUID carId) {
        producer.sendMessage(new MaintenanceReturnEvent(carId), "maintenance-return");
    }

}

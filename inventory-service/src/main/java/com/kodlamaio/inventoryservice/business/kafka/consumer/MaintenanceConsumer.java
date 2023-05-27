package com.kodlamaio.inventoryservice.business.kafka.consumer;

import com.kodlamaio.commonpackage.events.maintenance.MaintenanceCreatedEvent;
import com.kodlamaio.commonpackage.events.rental.RentalDeletedEvent;
import com.kodlamaio.inventoryservice.business.abstracts.CarService;
import com.kodlamaio.inventoryservice.entities.enums.State;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaintenanceConsumer {
    private final CarService service;

    @KafkaListener(
        topics = "maintenance-created",
        groupId =  "1"
    )
    public void consume(MaintenanceCreatedEvent event) {
        service.changeCarStateByCarId(State.Maintenance, event.getCarId());
        log.info("maintenance-created event consumed by inventory-service {}", event.toString());
    }


    @KafkaListener(
        topics = "maintenance-returned",
        groupId = "1"
    )
    public void consume(RentalDeletedEvent event) {
        service.changeCarStateByCarId(State.Available, event.getCarId());
        log.info("Rental deleted event consumed by inventory-service {}", event.toString());
    }
}

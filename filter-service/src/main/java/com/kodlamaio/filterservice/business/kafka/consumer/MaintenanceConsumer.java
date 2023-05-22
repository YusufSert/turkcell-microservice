package com.kodlamaio.filterservice.business.kafka.consumer;

import com.kodlamaio.commonpackage.events.maintenance.MaintenanceCreatedEvent;
import com.kodlamaio.commonpackage.events.maintenance.MaintenanceDeletedEvent;
import com.kodlamaio.commonpackage.events.maintenance.MaintenanceReturnEvent;
import com.kodlamaio.commonpackage.events.rental.RentalDeletedEvent;
import com.kodlamaio.filterservice.business.abstracts.FilterService;
import com.kodlamaio.filterservice.entities.Filter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaintenanceConsumer {
    private final FilterService service;

    @KafkaListener(
        topics = "maintenance-created",
        groupId =  "2"
    )
    public void consume(MaintenanceCreatedEvent event) {
        Filter filter = service.getByCarId(event.getCarId());
        filter.setState("Maintenance");
        service.add(filter);
        log.info("maintenance-created event consumed by filter-service {}", event);
    }
    @KafkaListener(
        topics = "maintenance-returned",
        groupId = "2"
    )
    public void consume(MaintenanceReturnEvent event) {
        Filter filter = service.getByCarId(event.getCarId());
        filter.setState("Available");
        service.add(filter);
        log.info("Rental deleted event consumed by filter-service {}", event);
    }
}

package com.kodlamaio.inventoryservice.business.kafka.consumer;

import com.kodlamaio.commonpackage.events.rental.RentalCreatedEvent;
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
public class RentalConsumer {
    private final CarService service;

    @KafkaListener(
        topics = "rental-created",
        groupId = "inventory"
    )
    public void consume(RentalCreatedEvent event) {
        service.changeCarStateByCarId(State.Rented, event.getCarId());
        log.info("Rental created event consumed {}", event);
    }

    @KafkaListener(
        topics = "rental-deleted",
        groupId = "inventory"
    )
    public void consume(RentalDeletedEvent event) {
        service.changeCarStateByCarId(State.Available, event.getCarId());
        log.info("Rental deleted event consumed {}", event);
    }
}

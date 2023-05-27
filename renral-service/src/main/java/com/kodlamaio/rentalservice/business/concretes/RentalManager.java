package com.kodlamaio.rentalservice.business.concretes;

import com.kodlamaio.commonpackage.events.Invoice.InvoiceCreatedEvent;
import com.kodlamaio.commonpackage.events.rental.RentalCreatedEvent;
import com.kodlamaio.commonpackage.events.rental.RentalDeletedEvent;
import com.kodlamaio.commonpackage.kafka.producer.KafkaProducer;
import com.kodlamaio.commonpackage.utils.dto.responses.GetCarResponse;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.rentalservice.api.clients.inventory_rest_client.CarClient;
import com.kodlamaio.rentalservice.api.clients.paymentl_rest_client.PaymentClient;
import com.kodlamaio.rentalservice.business.abstracts.RentalService;
import com.kodlamaio.rentalservice.business.dto.requests.CreateRentalRequest;
import com.kodlamaio.rentalservice.business.dto.requests.UpdateRentalRequest;
import com.kodlamaio.rentalservice.business.dto.responses.CreateRentalResponse;
import com.kodlamaio.rentalservice.business.dto.responses.GetAllRentalsResponse;
import com.kodlamaio.rentalservice.business.dto.responses.GetRentalResponse;
import com.kodlamaio.rentalservice.business.dto.responses.UpdateRentalResponse;
import com.kodlamaio.rentalservice.business.rules.RentalBusinessRules;
import com.kodlamaio.rentalservice.entities.Rental;
import com.kodlamaio.rentalservice.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RentalManager implements RentalService {
    private final RentalRepository repository;
    private final ModelMapperService mapper;
    private final RentalBusinessRules rules;
    private final KafkaProducer producer;
    private final CarClient client;

    @Override
    public List<GetAllRentalsResponse> getAll() {
        var rentals = repository.findAll();

        return rentals
                .stream()
                .map(rental -> mapper.forResponse().map(rental, GetAllRentalsResponse.class))
                .toList();
    }

    @Override
    public GetRentalResponse getById(UUID id) {
        rules.checkIfRentalExists(id);
        var rental = repository.findById(id).orElseThrow();
        return mapper.forResponse().map(rental, GetRentalResponse.class);
    }

    @Override
    public CreateRentalResponse add(CreateRentalRequest request) {
        rules.checkIfCarAvailableForRent(request.getCarId());
        var rental = mapper.forRequest().map(request, Rental.class);
        rules.setUpRentalRecord(rental);
        rules.setUpAndMakePayment(request.getPaymentCardInfo(), rental.getTotalPrice());


        repository.save(rental);
        var invoiceEvent = makeInvoiceCreatedEvent(request);
        sendKafkaRentalCreatedEvent(request.getCarId());
        sendKafkaInvoiceCreatedEvent(invoiceEvent);
        return mapper.forResponse().map(rental, CreateRentalResponse.class);
    }

    public InvoiceCreatedEvent makeInvoiceCreatedEvent(CreateRentalRequest request) {
        InvoiceCreatedEvent event = new InvoiceCreatedEvent();
        GetCarResponse car = client.getCarById(request.getCarId());
        event.setBrandName(car.getModelBrandName());
        event.setPlate(car.getPlate());
        event.setCardHolder(request.getPaymentCardInfo().getCardHolderName());
        event.setModelYear(car.getModelYear());
        event.setDailyPrice(request.getDailyPrice());
        event.setTotalPrice(request.getDailyPrice() * request.getRentedForDays());
        event.setRentedForDays(request.getRentedForDays());
        event.setRentedAt(LocalDate.now());
        return event;
    }

    @Override
    public UpdateRentalResponse update(UUID id, UpdateRentalRequest request) {
        rules.checkIfRentalExists(id);
        var rental = mapper.forRequest().map(request, Rental.class);
        rental.setId(id);
        repository.save(rental);
        return mapper.forResponse().map(rental, UpdateRentalResponse.class);
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfRentalExists(id);
        sendKafkaRentalDeletedEvent(id);
        repository.deleteById(id);
    }




    private void sendKafkaRentalCreatedEvent(UUID carId) {
        producer.sendMessage(new RentalCreatedEvent(carId), "rental-created");
    }
    private void sendKafkaInvoiceCreatedEvent(InvoiceCreatedEvent event) {
        producer.sendMessage(event, "invoice-created");
    }

    private void sendKafkaRentalDeletedEvent(UUID id) {
        var carId = repository.findById(id).orElseThrow().getCarId();
        producer.sendMessage(new RentalDeletedEvent(carId), "rental-deleted");
    }
}

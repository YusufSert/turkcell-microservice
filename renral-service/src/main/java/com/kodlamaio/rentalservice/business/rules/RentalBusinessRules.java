package com.kodlamaio.rentalservice.business.rules;

import com.kodlamaio.commonpackage.events.Invoice.CreateInvoiceEvent;
import com.kodlamaio.commonpackage.utils.dto.responses.ClientCarResponse;
import com.kodlamaio.rentalservice.api.clients.paymentl_rest_client.RentalPaymentRequest;
import com.kodlamaio.commonpackage.utils.dto.responses.ClientSuccessResponse;
import com.kodlamaio.commonpackage.utils.exceptions.BusinessException;
import com.kodlamaio.rentalservice.api.clients.inventory_rest_client.CarClient;
import com.kodlamaio.rentalservice.api.clients.paymentl_rest_client.PaymentClient;
import com.kodlamaio.rentalservice.business.dto.requests.CreateRentalRequest;
import com.kodlamaio.rentalservice.entities.PaymentDetails;
import com.kodlamaio.rentalservice.entities.Rental;
import com.kodlamaio.rentalservice.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RentalBusinessRules {
    private final RentalRepository repository;
    private final CarClient carClient;
    private final PaymentClient rentalClient;

    public void checkIfRentalExists(UUID id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("RENTAL_NOT_EXISTS");
        }
    }

    public void checkIfCarAvailableForRent(UUID carId) {
        var response = carClient.checkIfCarAvailableInInventory(carId);
        if (!response.isSuccess()) {
            throw new BusinessException(response.getMessage());
        }
    }

    public void setUpRentalRecord(Rental rental) {
        rental.setId(UUID.randomUUID());
        rental.setTotalPrice(rental.getDailyPrice() * rental.getRentedForDays());
        rental.setRentedAt(LocalDate.now());
    }

    public void setUpPaymentRequestAndMakePayment(PaymentDetails details, double price) {
        RentalPaymentRequest paymentRequest = new RentalPaymentRequest(
            details.getCardNumber(),
            details.getCardCvv(),
            details.getYear(),
            details.getMonth(),
            details.getCardHolderName(),
            price
        );
        makePayment(paymentRequest);
    }
    public void makePayment(RentalPaymentRequest request) throws BusinessException {
        ClientSuccessResponse response = rentalClient.makePayment(request);
        if(!response.isSuccess()) {
            throw new BusinessException(response.getMessage());
        }
    }

    public CreateInvoiceEvent setUpCreateInvoiceEvent(CreateRentalRequest request) {
        ClientCarResponse car = carClient.getCarById(request.getCarId());
        return new CreateInvoiceEvent(
            car.getId(),
            request.getPaymentDetails().getCardHolderName(),
            car.getModelBrandName(),
            car.getPlate(),
            car.getModelYear(),
            request.getDailyPrice(),
            LocalDate.now(),
            request.getRentedForDays(),
            request.getDailyPrice() * request.getRentedForDays()
        );
    }
}

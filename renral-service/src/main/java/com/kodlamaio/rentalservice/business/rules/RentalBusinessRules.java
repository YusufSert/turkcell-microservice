package com.kodlamaio.rentalservice.business.rules;

import com.kodlamaio.rentalservice.api.clients.paymentl_rest_client.RentalPaymentRequest;
import com.kodlamaio.commonpackage.utils.dto.responses.ClientResponse;
import com.kodlamaio.commonpackage.utils.exceptions.BusinessException;
import com.kodlamaio.rentalservice.api.clients.inventory_rest_client.CarClient;
import com.kodlamaio.rentalservice.api.clients.paymentl_rest_client.PaymentClient;
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
        rental.setId(null);
<<<<<<< HEAD
        rental.setTotalPrice(getTotalPrice(rental));
        rental.setRentedAt(LocalDate.now());
    }

    private double getTotalPrice(Rental rental) {
        return rental.getDailyPrice() * rental.getRentedForDays();
=======
        rental.setTotalPrice(getTotalPrice(rental.getDailyPrice(), rental.getRentedForDays()));
        rental.setRentedAt(LocalDate.now());
    }

    public double getTotalPrice(Double dailyPrice, int rentedForDays) {
        return dailyPrice * rentedForDays;
>>>>>>> origin/main
    }


    public void setUpAndMakePayment(PaymentDetails details, double price) {
        var paymentRequest = new RentalPaymentRequest();
        paymentRequest.setPrice(price);
        paymentRequest.setCardNumber(details.getCardNumber());
        paymentRequest.setCardCvv(details.getCardCvv());
        paymentRequest.setMonth(details.getMonth());
        paymentRequest.setYear(details.getYear());
        paymentRequest.setCardHolderName(details.getCardHolderName());
        makePayment(paymentRequest);
    }
    public void makePayment(RentalPaymentRequest request) {
        ClientResponse response = rentalClient.makePayment(request);
        if(!response.isSuccess()) {
            throw new BusinessException(response.getMessage());
        }
    }
}

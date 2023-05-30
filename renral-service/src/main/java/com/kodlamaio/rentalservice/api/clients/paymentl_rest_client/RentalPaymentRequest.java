package com.kodlamaio.rentalservice.api.clients.paymentl_rest_client;

import com.kodlamaio.commonpackage.utils.dto.requests.PaymentRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class RentalPaymentRequest extends PaymentRequest {
    public RentalPaymentRequest(String cardNumber, String cardCvv, int year, int month, String cardHolderName, double price) {
        super(cardNumber, cardCvv, year, month, cardHolderName, price);
    }
}

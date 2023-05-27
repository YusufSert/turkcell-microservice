package com.kodlamaio.paymentservice.business.dto.request.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CreatePaymentRequest {

    private String cardNumber;
    private String cvv;
    private String year;
    private String month;
    private String cardHolderName;
    private int balance;
}

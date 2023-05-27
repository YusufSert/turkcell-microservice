package com.kodlamaio.rentalservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentDetails {
    private String cardNumber;
    private String cardCvv;
    private int year;
    private int month;
    private String cardHolderName;
}

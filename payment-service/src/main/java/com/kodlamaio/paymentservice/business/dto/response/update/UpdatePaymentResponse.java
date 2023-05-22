package com.kodlamaio.paymentservice.business.dto.response.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UpdatePaymentResponse {
    private UUID id;
    private String cardNumber;
    private String cvv;
    private String year;
    private String month;
    private String cardHolderName;
    private int balance;
}

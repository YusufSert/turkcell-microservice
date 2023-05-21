package com.kodlamaio.commonpackage.utils.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private String cardNumber;
    private String cardCvv;
    private int year;
    private int month;
    private String cardHolderName;
}

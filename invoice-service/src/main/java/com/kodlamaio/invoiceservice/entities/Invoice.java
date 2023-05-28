package com.kodlamaio.invoiceservice.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    UUID id;
    UUID carId;
    String cardHolder;
    String brandName;
    String plate;
    int modelYear;
    double dailyPrice;
    LocalDate rentedAt;
    int rentedForDays;
    double totalPrice;
}

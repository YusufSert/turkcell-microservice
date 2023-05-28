package com.kodlamaio.commonpackage.events.Invoice;

import com.kodlamaio.commonpackage.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceCreatedEvent extends Event {
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

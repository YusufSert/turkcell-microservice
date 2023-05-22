package com.kodlamaio.paymentservice.repository;

import com.kodlamaio.paymentservice.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    @Query("select p from Payment p where p.cardNumber =?1  and p.cvv=?2 and p.year=?3 and p.month=?4 and p.cardHolderName=?5")
    Payment validateCard(String cardNumber,String cvv,String year,String month,String cardHolderName);

    boolean existsByCardNumberAndCardHolderNameAndYearAndMonthAndCvv(
        String cardNumber, String cardHolder, int cardExpirationYear, int cardExpirationMonth, String cardCvv);

    Payment findPaymentByCardNumber(String cardNumber);

}

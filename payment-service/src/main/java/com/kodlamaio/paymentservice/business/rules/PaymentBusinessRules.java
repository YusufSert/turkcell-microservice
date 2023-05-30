package com.kodlamaio.paymentservice.business.rules;

import com.kodlamaio.commonpackage.utils.dto.requests.PaymentRequest;
import com.kodlamaio.commonpackage.utils.exceptions.BusinessException;
import com.kodlamaio.paymentservice.entities.Payment;
import com.kodlamaio.paymentservice.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentBusinessRules {
    private final PaymentRepository repository;

    public void checkIfPaymentExist(UUID id) throws BusinessException{
        if(!repository.existsById(id)) {
            throw new BusinessException("Payment record not found");
        }
    }

    public void makePayment(PaymentRequest request) throws BusinessException{
        validatePaymentDetails(request);
        makeMoneyTransaction(request.getCardNumber(), request.getPrice());
    }

    public void validatePaymentDetails(PaymentRequest request) throws BusinessException {
        var isExist = repository.existsByCardNumberAndCardHolderNameAndYearAndMonthAndCvv(
            request.getCardNumber(), request.getCardHolderName(), request.getYear(), request.getMonth(), request.getCardCvv()
        );
        if(!isExist) {
            throw new BusinessException("Payment not found");
        }
    }

     public void makeMoneyTransaction(String cardNumber, double price) throws BusinessException {
        Payment payment = repository.findPaymentByCardNumber(cardNumber);
        if(payment.getBalance() < price) {
            throw new BusinessException("Paran yok şuan fakirsin malesef");
        }
        payment.setBalance(payment.getBalance() - price);
        repository.save(payment);
    }

}

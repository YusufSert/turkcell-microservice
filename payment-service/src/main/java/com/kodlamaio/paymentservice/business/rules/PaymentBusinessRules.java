package com.kodlamaio.paymentservice2.business.rules;

import com.kodlamaio.paymentservice2.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentBusinessRules {
    private final PaymentRepository repository;
}

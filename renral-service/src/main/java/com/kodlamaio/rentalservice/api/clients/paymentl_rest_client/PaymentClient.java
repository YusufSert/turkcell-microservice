package com.kodlamaio.rentalservice.api.clients.paymentl_rest_client;

import com.kodlamaio.commonpackage.utils.dto.responses.ClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "payment-service", fallback = PaymentClientFallback.class)
public interface PaymentClient {
    @PostMapping("/api/payments/make-payment")
    public ClientResponse makePayment(@RequestBody RentalPaymentRequest request);
}

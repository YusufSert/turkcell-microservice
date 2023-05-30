package com.kodlamaio.rentalservice.api.clients.paymentl_rest_client;

import com.kodlamaio.commonpackage.utils.dto.responses.ClientSuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "payment-service", fallback = PaymentClientFallback.class)
public interface PaymentClient {
    @PostMapping("/api/payments/make-payment")
    public ClientSuccessResponse makePayment(@RequestBody RentalPaymentRequest request);
}

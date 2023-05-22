package com.kodlamaio.rentalservice.api.clients.paymentl_rest_client;

import com.kodlamaio.commonpackage.utils.dto.responses.ClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentClientFallback implements PaymentClient {


    @Override
    public ClientResponse makePayment(RentalPaymentRequest request){
        log.info("Couldn't connect to payment-sevice server not working properly !");
        return new ClientResponse(false, "Couldn't connect to payment-service");
    }

}

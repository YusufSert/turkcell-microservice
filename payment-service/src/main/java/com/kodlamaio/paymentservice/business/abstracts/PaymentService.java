package com.kodlamaio.paymentservice.business.abstracts;


import com.kodlamaio.commonpackage.utils.dto.requests.PaymentRequest;
import com.kodlamaio.commonpackage.utils.dto.responses.ClientSuccessResponse;
import com.kodlamaio.paymentservice.business.dto.request.create.CreatePaymentRequest;
import com.kodlamaio.paymentservice.business.dto.request.update.UpdatePaymentRequest;
import com.kodlamaio.paymentservice.business.dto.response.create.CreatePaymentResponse;
import com.kodlamaio.paymentservice.business.dto.response.get.GetAllPaymentResponse;
import com.kodlamaio.paymentservice.business.dto.response.get.GetPaymentResponse;
import com.kodlamaio.paymentservice.business.dto.response.update.UpdatePaymentResponse;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    List<GetAllPaymentResponse> getAll();
    GetPaymentResponse getById(UUID id);
    CreatePaymentResponse add(CreatePaymentRequest request);

    <T extends PaymentRequest> ClientSuccessResponse makePayment(T request);

    UpdatePaymentResponse update(UUID id, UpdatePaymentRequest request);
    void delete(UUID id);

}
